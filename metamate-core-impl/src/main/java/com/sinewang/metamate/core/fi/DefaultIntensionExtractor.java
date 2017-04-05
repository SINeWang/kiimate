package com.sinewang.metamate.core.fi;

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
    public Intension parseForm1(String ownerId, String group, String name, String tree, DeclareIntensionApi.IntensionForm1 intensionForm) {
        String extId = extensionExtractor.hashId(ownerId, group, name, tree);
        Intension intension = DataTools.copy(intensionForm, Intension.class);
        String id = hashId(extId, intension.getField());
        intension.setExtId(extId);
        intension.setId(id);
        return intension;
    }

    @Override
    public Intension parseForm2(DeclareIntensionApi.IntensionForm2 intensionForm) {
        Intension intension = DataTools.copy(intensionForm, Intension.class);
        String id = hashId(intension.getExtId(), intension.getField());
        intension.setId(id);
        return intension;
    }

    public String hashId(String extId, String field) {
        return HashTools.hashHex(extId, field);
    }

}
