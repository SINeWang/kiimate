package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.SearchAssetsApi;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Component
public class DefaultSearchAssetsApi implements SearchAssetsApi {

    @Autowired
    private AssetPublicationDai assetPublicationDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public List<Assets> search(ReadContext context, QueryForm form) {
        List<AssetPublicationDai.Assets> assetsList = assetPublicationDai.queryAssets(context.getOwnerId(), form.getQuery());

        List<Assets> assets = new ArrayList<>();
        for (AssetPublicationDai.Assets assetDb : assetsList) {
            List<InstanceDai.Instance> instances = instanceDai.selectInstanceByPubSet(assetDb.getPubSet());
            Assets asset = BasicCopy.from(Assets.class, assetDb);
            String rootExtId = modelSubscriptionDai.getLatestRootExtIdByOwnerSubscription(context.getOwnerId(), assetDb.getModelSubId());
            Map<String, Object> map = instanceTransformer.from(instances, rootExtId);
            asset.setMap(map);
            assets.add(asset);
        }
        return assets;
    }


}
