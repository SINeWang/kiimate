package com.sinewang.metamate.core.fi;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.magnet.util.HashUtil;
import wang.yanjiong.magnet.xi.boundary.Context;
import wang.yanjiong.metamate.core.api.CreateIntensionApi;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

import java.util.UUID;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultIntensionExtractor implements AnIntensionExtractor {

    @Override
    public Intension parse(CreateIntensionApi.Form form) {
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
        return HashUtil.hashHex(extId, field);
    }

}
