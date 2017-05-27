package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitAssetApi;
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
 * Created by WangYanJiong on 21/05/2017.
 */
@Component
public class DefaultVisitAssetApi implements VisitAssetApi {

    @Autowired
    private LoadAssetsDai loadAssetsDai;

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
        LoadAssetsDai.ChannelPubSet channel = ValueMapping.from(LoadAssetsDai.ChannelPubSet.class, form, context);
        LoadAssetsDai.Assets assetDb = loadAssetsDai.fetchAssets(channel);
        return transform(context, assetDb);
    }

    @Override
    public Asset visit(ReadContext context, GroupNameForm form) throws NotFound {
        LoadAssetsDai.ChannelGroupName channel = ValueMapping.from(LoadAssetsDai.ChannelGroupName.class, form, context);
        LoadAssetsDai.Assets assetDb = loadAssetsDai.fetchAssets(channel);
        return transform(context, assetDb);
    }

    private Asset transform(ReadContext context, LoadAssetsDai.Assets assetDb) throws NotFound {
        if (assetDb == null) {
            throw new NotFound(KeyFactorTools.find(DefaultSearchAssetsApi.Assets.class));
        }
        List<InstanceDai.Instance> instances = instanceDai.selectInstanceByPubSet(assetDb.getPubSet());
        Asset asset = ValueMapping.from(Asset.class, assetDb);
        ModelSubscriptionDai.ExtensionId rootExtId = modelSubscriptionDai.getLatestRootExtIdByOwnerSubscription(context.getOwnerId(), assetDb.getModelSubId());
        Map<String, Object> map = instanceTransformer.toTimedValue(instances, rootExtId.getId());

        IntensionDai.ChannelExtension extension = ValueMapping.from(IntensionDai.ChannelExtension.class, rootExtId);

        List<IntensionDai.Intension> intensionList = intensionDai.loadIntensions(extension);
        List<Intension> intensions = ValueMapping.from(Intension.class, intensionList);
        asset.setIntensions(intensions);
        asset.setMap(map);
        asset.setOwnerId(context.getOwnerId());
        return asset;
    }
}
