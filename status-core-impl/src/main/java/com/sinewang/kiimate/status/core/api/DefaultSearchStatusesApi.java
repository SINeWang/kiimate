package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SearchStatusesApi;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Component
public class DefaultSearchStatusesApi implements SearchStatusesApi {

    @Autowired
    private AssetDai assetDai;

    @Override
    public List<Statuses> search(ReadContext context, QueryForm form) {
        AssetDai.ClueGroup clue = ValueMapping.from(AssetDai.ClueGroup.class, context);
        clue.setGroup(form.getQuery());
        List<AssetDai.Asset> assetList = assetDai.query(clue);
        return ValueMapping.from(Statuses.class, assetList);
    }


}
