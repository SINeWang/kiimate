package com.sinewang.kiimate.status.core.api;

import com.sun.tools.corba.se.idl.constExpr.Not;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.kiimate.status.core.api.RefreshStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.AnInstanceExtractor;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultRefreshStatusApi implements RefreshStatusApi {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRefreshStatusApi.class);

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AnInstanceExtractor instanceExtractor;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private AnModelRestorer modelRestorer;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Receipt commit(WriteContext context, SubIdForm form) throws BadRequest, Conflict,NotFound, Panic {
        ModelSubscriptionDai.ChannelSubId channel = ValueMapping.from(ModelSubscriptionDai.ChannelSubId.class, context, form);

        ModelSubscriptionDai.ModelPubSet model = modelSubscriptionDai.getModelPubSetByOwnerSubscription(channel);


        IntensionDai.ChannelLastExtension lastExtension = new IntensionDai.ChannelLastExtension();
        lastExtension.setId(model.getRootExtId());
        lastExtension.setBeginTime(model.getBeginTime());

        Map<String, IntensionDai.Record> dict = modelRestorer.restoreAsIntensionDict(lastExtension);

        List<AnInstanceExtractor.Instance> instances = instanceExtractor.extract(context, form, dict);

        List<InstanceDai.Record> record1 = ValueMapping.from(InstanceDai.Record.class, instances);

        try {
            instanceDai.remember(record1);
        } catch (InstanceDai.InstanceDuplicated instanceDuplicated) {
            throw new Conflict(KeyFactorTools.find(InstanceDai.Record.class));
        }


        IntensionDai.ChannelLastExtension rootExtension = ValueMapping.from(IntensionDai.ChannelLastExtension.class, model);

        rootExtension.setId(model.getRootExtId());

        InstanceDai.ChannelStatusId modelSubId = ValueMapping.from(InstanceDai.ChannelStatusId.class, form);

        List<InstanceDai.Instance> newInstances = instanceDai.loadInstances(modelSubId);

        List<IntensionDai.Record> recordList = intensionDai.loadLast(rootExtension);
        List<Intension> intensions = ValueMapping.from(Intension.class, recordList);

        Map<String, Object> map = instanceTransformer.toTimedValue(newInstances, model);

        Receipt receipt = ValueMapping.from(Receipt.class, form, context);
        receipt.setMap(map);
        receipt.setIntensions(intensions);

        return receipt;
    }

}
