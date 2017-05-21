package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.SearchAssetsApi;
import one.kii.kiimate.status.core.api.VisitAssetsApi;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */
public class DefaultVisitAssetsApi implements VisitAssetsApi {

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
    public List<VisitAssetsApi.Assets> visit(ReadContext context, SearchAssetsApi.QueryForm form) {
        List<AssetPublicationDai.Assets> assetsList = assetPublicationDai.queryAssets(context.getOwnerId(), form.getQuery());

        List<VisitAssetsApi.Assets> assets = new ArrayList<>();
        for (AssetPublicationDai.Assets assetDb : assetsList) {
            List<InstanceDai.Instance> instances = instanceDai.selectInstanceByPubSet(assetDb.getPubSet());
            VisitAssetsApi.Assets asset = BasicCopy.from(VisitAssetsApi.Assets.class, assetDb);
            String rootExtId = modelSubscriptionDai.getLatestRootExtIdByOwnerSubscription(context.getOwnerId(), assetDb.getModelSubId());
            Map<String, Object> map = instanceTransformer.from(instances, rootExtId);
            List<IntensionDai.Intension> intensionList = intensionDai.selectIntensionsByExtId(rootExtId);
            List<VisitAssetsApi.Intension> intensions = BasicCopy.from(VisitAssetsApi.Intension.class, intensionList);
            asset.setIntensions(intensions);
            asset.setMap(map);
            assets.add(asset);
        }
        return assets;
    }
}
