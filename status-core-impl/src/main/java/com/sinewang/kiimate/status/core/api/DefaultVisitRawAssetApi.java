package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitRawAssetsApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.LoadAssetsDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 23/05/2017.
 */
@Component
public class DefaultVisitRawAssetApi implements VisitRawAssetsApi {

    @Autowired
    private LoadAssetsDai loadAssetsDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Map<String, Object> visit(ReadContext context, PubSetForm form) throws NotFound {
        LoadAssetsDai.ChannelPubSet channel = ValueMapping.from(LoadAssetsDai.ChannelPubSet.class, form, context);
        LoadAssetsDai.Assets assetDb = loadAssetsDai.loadAssets(channel);
        return transform(context, assetDb);
    }

    @Override
    public Map<String, Object> visit(ReadContext context, GroupNameForm form) throws NotFound {
        LoadAssetsDai.ChannelGroupName channel = ValueMapping.from(LoadAssetsDai.ChannelGroupName.class, form, context);
        LoadAssetsDai.Assets assetDb = loadAssetsDai.loadAssets(channel);
        return transform(context, assetDb);
    }

    private Map<String, Object> transform(ReadContext context, LoadAssetsDai.Assets assetDb) throws NotFound {
        InstanceDai.ChannelStatusPubSet statusPubSet = ValueMapping.from(InstanceDai.ChannelStatusPubSet.class, assetDb);
        List<InstanceDai.Instance> instances = instanceDai.loadInstances(statusPubSet);

        ModelSubscriptionDai.ChannelSubId channel = ValueMapping.from(ModelSubscriptionDai.ChannelSubId.class, context, assetDb);

        ModelSubscriptionDai.ModelPubSet model = modelSubscriptionDai.getModelPubSetByOwnerSubscription(channel);
        return instanceTransformer.toRawValue(instances, model);
    }
}
