package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitRawAssetApi;
import one.kii.kiimate.status.core.dai.AssetDai;
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
 * Created by WangYanJiong on 23/05/2017.
 */
@Component
public class DefaultVisitRawAssetApi implements VisitRawAssetApi {

    @Autowired
    private AssetDai assetDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;


    @Override
    public Map<String, Object> visit(ReadContext context, GroupNameForm form) throws BadRequest, NotFound, Panic {
        AssetDai.ChannelGroupName channel = ValueMapping.from(AssetDai.ChannelGroupName.class, form, context);
        AssetDai.Assets assetDb = assetDai.load(channel);

        InstanceDai.ChannelAssetId id = ValueMapping.from(InstanceDai.ChannelAssetId.class, assetDb);

        List<InstanceDai.Record> records = instanceDai.loadInstances(id);

        ModelSubscriptionDai.StatusId statusId = new ModelSubscriptionDai.StatusId();

        statusId.setId(records.get(0).getSubId());

        ModelSubscriptionDai.ModelPubSet model = modelSubscriptionDai.getModelPubSetByStatusId(statusId);
        return instanceTransformer.toRawValue(records, model);
    }
}
