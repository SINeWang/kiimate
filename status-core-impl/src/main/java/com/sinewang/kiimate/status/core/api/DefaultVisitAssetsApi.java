package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitAssetsApi;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.beans.utils.KeyFactorTools;
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
    public Assets visit(ReadContext context, PubSetForm form) throws NotFound {
        AssetPublicationDai.Assets assetDb = assetPublicationDai.selectAssets(context.getOwnerId(), form.getPubSet(), form.getStability(), form.getVersion());

        return transform(context, assetDb);
    }

    @Override
    public Assets visit(ReadContext context, GroupNameForm form) throws NotFound {
        AssetPublicationDai.Assets assetDb = assetPublicationDai.selectAssets(context.getOwnerId(), form.getGroup(), form.getName(), form.getStability(), form.getVersion());
        if (assetDb == null) {
            throw new NotFound(KeyFactorTools.find(assetDb));
        }
        return transform(context, assetDb);
    }

    private Assets transform(ReadContext context, AssetPublicationDai.Assets assetDb) {
        List<InstanceDai.Instance> instances = instanceDai.selectInstanceByPubSet(assetDb.getPubSet());
        Assets asset = BasicCopy.from(Assets.class, assetDb);
        String rootExtId = modelSubscriptionDai.getLatestRootExtIdByOwnerSubscription(context.getOwnerId(), assetDb.getModelSubId());
        Map<String, Object> map = instanceTransformer.from(instances, rootExtId);
        List<IntensionDai.Intension> intensionList = intensionDai.selectIntensionsByExtId(rootExtId);
        List<Intension> intensions = BasicCopy.from(Intension.class, intensionList);
        asset.setIntensions(intensions);
        asset.setMap(map);
        asset.setOwnerId(context.getOwnerId());
        return asset;
    }
}
