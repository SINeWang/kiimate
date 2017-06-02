package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultVisitStatusApi implements VisitStatusApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Receipt visit(ReadContext context, Form form) throws NotFound {
        ModelSubscriptionDai.ChannelSubId channel = ValueMapping.from(ModelSubscriptionDai.ChannelSubId.class, form, context);

        ModelSubscriptionDai.ModelPubSet model = modelSubscriptionDai.getModelPubSetByOwnerSubscription(channel);

        IntensionDai.ChannelLastExtension last = new IntensionDai.ChannelLastExtension();
        last.setId(model.getRootExtId());
        last.setBeginTime(model.getBeginTime());

        List<IntensionDai.Record> records = intensionDai.loadLast(last);
        List<Intension> intensions = ValueMapping.from(Intension.class, records);


        InstanceDai.ChannelModelSubId subId = ValueMapping.from(InstanceDai.ChannelModelSubId.class, form);
        List<InstanceDai.Instance> instances = instanceDai.loadInstances(subId);

        Map<String, Object> map = instanceTransformer.toTimedValue(instances, model);

        Receipt receipt = ValueMapping.from(Receipt.class, form, context);
        receipt.setMap(map);
        receipt.setIntensions(intensions);

        return receipt;
    }


}
