package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.kiimate.status.core.api.RefreshStatusApi;
import one.kii.kiimate.status.core.api.VisitStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.AnInstanceExtractor;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
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
    public Receipt commit(WriteContext context, SubIdForm form) throws NotFound, Conflict {

        ModelSubscriptionDai.ExtensionId rootExtId = modelSubscriptionDai.getLatestRootExtIdByOwnerSubscription(
                context.getOwnerId(), form.getSubId());

        if (rootExtId == null) {
            throw new NotFound(new String[]{context.getOwnerId(), form.getSubId()});
        }

        Map<String, IntensionDai.Intension> dict = modelRestorer.restoreAsIntensionDict(rootExtId.getId());

        List<AnInstanceExtractor.Instance> instances = instanceExtractor.extract(context, form.getSubId(), form.getMap(), dict);

        List<InstanceDai.Record> record1 = ValueMapping.from(InstanceDai.Record.class, instances);

        try {
            instanceDai.insert(record1);
        } catch (InstanceDai.InstanceDuplicated instanceDuplicated) {
            throw new Conflict(form.getSubId());
        }


        IntensionDai.ChannelExtension rootExtension = ValueMapping.from(IntensionDai.ChannelExtension.class, rootExtId);

        List<InstanceDai.Instance> newInstances = instanceDai.selectLatestInstanceBySubId(form.getSubId());

        List<IntensionDai.Intension> intensionList = intensionDai.loadLatestIntensions(rootExtension);
        List<VisitStatusApi.Intension> intensions = ValueMapping.from(VisitStatusApi.Intension.class, intensionList);


        Map<String, Object> map = instanceTransformer.toTimedValue(newInstances, rootExtId.getId());

        Receipt receipt = ValueMapping.from(Receipt.class, form, context);
        receipt.setMap(map);
        receipt.setIntensions(intensions);

        return receipt;
    }

    @Override
    public Receipt commit(WriteContext context, GroupNameTreeForm form) throws NotFound, Conflict {
        ModelSubscriptionDai.ChannelGroupNameTree channel = ValueMapping.from(ModelSubscriptionDai.ChannelGroupNameTree.class, form, context);
        ModelSubscriptionDai.ModelSubscription subscription = modelSubscriptionDai.selectSubscription(channel);
        if (subscription == null) {
            throw new NotFound(KeyFactorTools.find(ModelSubscriptionDai.ModelSubscription.class));
        }
        String subId = subscription.getId();
        SubIdForm subIdForm = ValueMapping.from(SubIdForm.class, form);
        subIdForm.setSubId(subId);
        return commit(context, subIdForm);
    }

}
