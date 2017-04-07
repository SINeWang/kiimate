package com.sinewang.metamate.core.fi;

import com.google.common.base.CaseFormat;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.DeclareIntensionApi;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultIntensionExtractor implements AnIntensionExtractor {

    @Autowired
    private AnExtensionExtractor extensionExtractor;


    @Override
    public Intension parseForm(DeclareIntensionApi.IntensionForm intensionForm) {

        intensionForm.setField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, intensionForm.getField()));

        Intension intension = DataTools.copy(intensionForm, Intension.class);
        String id = hashId(intension.getExtId(), intension.getField());
        intension.setId(id);
        return intension;
    }

    public String hashId(String extId, String field) {
        return HashTools.hashHex(extId, field);
    }

}
