package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitRawAssetsApi;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.KeyFactorTools;
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
    private AssetPublicationDai assetPublicationDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Map<String, Object> visit(ReadContext context, PubSetForm form) throws NotFound {
        AssetPublicationDai.Assets assetDb = assetPublicationDai.selectAssets(context.getOwnerId(), form.getPubSet(), form.getStability(), form.getVersion());
        return transform(context, assetDb);
    }

    @Override
    public Map<String, Object> visit(ReadContext context, GroupNameForm form) throws NotFound {
        AssetPublicationDai.Assets assetDb = assetPublicationDai.selectAssets(context.getOwnerId(), form.getGroup(), form.getName(), form.getStability(), form.getVersion());
        return transform(context, assetDb);
    }

    private Map<String, Object> transform(ReadContext context, AssetPublicationDai.Assets assetDb) throws NotFound {
        if (assetDb == null) {
            throw new NotFound(KeyFactorTools.find(assetDb));
        }
        List<InstanceDai.Instance> instances = instanceDai.selectInstanceByPubSet(assetDb.getPubSet());
        String rootExtId = modelSubscriptionDai.getLatestRootExtIdByOwnerSubscription(context.getOwnerId(), assetDb.getModelSubId());
        return instanceTransformer.from(instances, rootExtId);
    }
}
