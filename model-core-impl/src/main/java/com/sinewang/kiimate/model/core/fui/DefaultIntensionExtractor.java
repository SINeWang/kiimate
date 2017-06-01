package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.kiimate.model.core.fui.AnIntensionExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 25/03/2017.
 */

@Component
public class DefaultIntensionExtractor implements AnIntensionExtractor {

    private static final Eid64Generator idgen = new Eid64Generator(1);

    @Override
    public Intension parseForm(DeclareIntensionApi.Form form) {

        form.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getField()));

        Intension intension = ValueMapping.from(Intension.class, form);
        intension.setId(idgen.born());
        return intension;
    }

}
