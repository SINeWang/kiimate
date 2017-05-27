package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SearchAssetsApi;
import one.kii.kiimate.status.core.dai.LoadAssetsDai;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.beans.utils.MagicCopy;
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
        LoadAssetsDai.ClueGroup clue = MagicCopy.from(LoadAssetsDai.ClueGroup.class, form);
        clue.setGroup(form.getQuery());
        List<LoadAssetsDai.Assets> assetsList = loadAssetsDai.queryAssets(clue);
        return BasicCopy.from(Assets.class, assetsList);
    }


}
