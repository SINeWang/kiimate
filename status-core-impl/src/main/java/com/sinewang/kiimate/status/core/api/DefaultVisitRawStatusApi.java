package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitRawStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultVisitRawStatusApi implements VisitRawStatusApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Map<String, Object> visit(ReadContext context, GroupNameTreeForm form) throws NotFound, BadRequest,Panic {
        ModelSubscriptionDai.ChannelGroupNameTree channel = ValueMapping.from(ModelSubscriptionDai.ChannelGroupNameTree.class, form, context);

        ModelSubscriptionDai.Status subscription = modelSubscriptionDai.selectSubscription(channel);

        ModelSubscriptionDai.StatusId subId = ValueMapping.from(ModelSubscriptionDai.StatusId.class, context, subscription);

        ModelSubscriptionDai.ModelPubSet modelPubSet = modelSubscriptionDai.getModelPubSetByStatusId(subId);

        InstanceDai.ChannelAssetId statusId = ValueMapping.from(InstanceDai.ChannelAssetId.class, subscription);
        List<InstanceDai.Record> records = instanceDai.loadInstances(statusId);

        return instanceTransformer.toRawValue(records, modelPubSet);
    }

}
