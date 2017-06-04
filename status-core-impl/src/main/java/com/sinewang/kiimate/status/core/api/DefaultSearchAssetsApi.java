package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SearchAssetsApi;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 04/06/2017.
 */
@Component
public class DefaultSearchAssetsApi implements SearchAssetsApi {

    @Autowired
    private AssetDai assetDai;

    @Override
    public List<Assets> search(ReadContext context, QueryForm form) throws BadRequest {
        AssetDai.ClueGroup clue = ValueMapping.from(AssetDai.ClueGroup.class, form);
        return ValueMapping.from(Assets.class, assetDai.query(clue));
    }
}
