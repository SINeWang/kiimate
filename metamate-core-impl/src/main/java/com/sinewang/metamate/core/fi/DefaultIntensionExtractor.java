package com.sinewang.metamate.core.fi;

import one.kii.summer.bound.Context;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.SetIntensionApi;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

import java.util.UUID;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultIntensionExtractor implements AnIntensionExtractor {

    @Override
    public Intension parse(SetIntensionApi.Form form) {
        Context context = new Context();
        context.setProcessId(UUID.randomUUID().toString());
        form.setContext(context);

        Intension intension = new Intension();
        BeanUtils.copyProperties(form, intension);
        String id = hashId(intension.getExtId(), intension.getField());
        intension.setId(id);
        return intension;
    }

    public String hashId(String extId, String field) {
        return HashTools.hashHex(extId, field);
    }

}
