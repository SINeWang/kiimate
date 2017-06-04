package com.sinewang.kiimate.status.core.fui;

import one.kii.kiimate.status.core.api.PublishAssetApi;
import one.kii.kiimate.status.core.fui.AssetPublicationExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 19/05/2017.
 */

@Component
public class DefaultAssetPublicationExtractor implements AssetPublicationExtractor {
    @Override
    public Informal extract(WriteContext context, PublishAssetApi.Form form) {
        Informal informal = ValueMapping.from(Informal.class, form);
        if (TREE_LATEST.equals(form.getStability())) {
            informal.setVersion(VERSION_HEAD);
        }
        return informal;
    }

}
