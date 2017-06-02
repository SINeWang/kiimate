package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitAssetApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.AssetsDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */
@Component
public class DefaultVisitAssetApi implements VisitAssetApi {

    @Autowired
    private AssetsDai assetsDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Asset visit(ReadContext context, PubSetForm form) throws NotFound {
        AssetsDai.ChannelPubSet channel = ValueMapping.from(AssetsDai.ChannelPubSet.class, form, context);
        AssetsDai.Asset assetDb = assetsDai.load(channel);
        return transform(context, assetDb);
    }

    @Override
    public Asset visit(ReadContext context, GroupNameForm form) throws NotFound {
        AssetsDai.ChannelGroupName channel = ValueMapping.from(AssetsDai.ChannelGroupName.class, form, context);
        AssetsDai.Asset record = assetsDai.load(channel);
        return transform(context, record);
    }

    private Asset transform(ReadContext context, AssetsDai.Asset assetDb) throws NotFound {
        InstanceDai.ChannelStatusPubSet statusPubSet = ValueMapping.from(InstanceDai.ChannelStatusPubSet.class, assetDb);
        List<InstanceDai.Instance> instances = instanceDai.loadInstances(statusPubSet);
        Asset asset = ValueMapping.from(Asset.class, assetDb);


        ModelSubscriptionDai.ChannelSubId modelSubId = ValueMapping.from(ModelSubscriptionDai.ChannelSubId.class, context, assetDb);
        ModelSubscriptionDai.ModelPubSet model = modelSubscriptionDai.getModelPubSetByOwnerSubscription(modelSubId);
        Map<String, Object> map = instanceTransformer.toTimedValue(instances, model);

        IntensionDai.ChannelLastExtension rootExtension = ValueMapping.from(IntensionDai.ChannelLastExtension.class, model);
        rootExtension.setId(model.getRootExtId());
        List<IntensionDai.Record> recordList = intensionDai.loadLast(rootExtension);
        List<Intension> intensions = ValueMapping.from(Intension.class, recordList);
        asset.setIntensions(intensions);
        asset.setMap(map);
        asset.setOwnerId(context.getOwnerId());
        return asset;
    }
}
