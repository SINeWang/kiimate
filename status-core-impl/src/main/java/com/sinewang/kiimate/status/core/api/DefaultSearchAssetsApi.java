package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SearchAssetsApi;
import one.kii.kiimate.status.core.dai.LoadAssetsDai;
import one.kii.summer.beans.utils.ValueMapping;
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
    private LoadAssetsDai loadAssetsDai;

    @Override
    public List<Assets> search(ReadContext context, QueryForm form) {
        LoadAssetsDai.ClueGroup clue = ValueMapping.from(LoadAssetsDai.ClueGroup.class, context);
        clue.setGroup(form.getQuery());
        List<LoadAssetsDai.Assets> assetsList = loadAssetsDai.queryAssets(clue);
        return ValueMapping.from(Assets.class, assetsList);
    }


}
