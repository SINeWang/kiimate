package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SearchAssetsApi;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Component
public class DefaultSearchAssetsApi implements SearchAssetsApi {

    @Autowired
    private AssetPublicationDai assetPublicationDai;

    @Override
    public List<Assets> search(ReadContext context, QueryForm form) {
        List<AssetPublicationDai.Assets> assets = assetPublicationDai.queryAssets(context.getOwnerId(), form.getQuery());
        return BasicCopy.from(Assets.class, assets);
    }
}
