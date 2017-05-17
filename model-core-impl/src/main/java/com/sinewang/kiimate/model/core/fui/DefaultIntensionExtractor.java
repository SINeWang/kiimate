package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.kiimate.model.core.fui.AnIntensionExtractor;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 25/03/2017.
 */

@Component
public class DefaultIntensionExtractor implements AnIntensionExtractor {


    @Override
    public Intension parseForm(DeclareIntensionApi.Form form) {

        form.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getField()));

        Intension intension = DataTools.copy(form, Intension.class);
        hashId(intension);
        return intension;
    }

    @Override
    public void hashId(Intension intension) {
        String id = HashTools.hashHex(intension);
        intension.setId(id);
    }

}
