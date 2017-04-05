package com.sinewang.metamate.core.fi;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.DeclarePropApi;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultIntensionExtractor implements AnIntensionExtractor {

    @Override
    public Intension parse(DeclarePropApi.PropForm propForm) {
        Intension intension = DataTools.copy(propForm, Intension.class);
        String id = hashId(intension.getExtId(), intension.getField());
        intension.setId(id);
        return intension;
    }

    public String hashId(String extId, String field) {
        return HashTools.hashHex(extId, field);
    }

}
