package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.RemoveIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 09/05/2017.
 */
@Component
public class DefaultRemoveIntensionApi implements RemoveIntensionApi {

    @Autowired
    private IntensionDai intensionDai;

    @Override
    public Receipt removeIntension(WriteContext context, Form form) throws Conflict {
        intensionDai.removeIntension(form.getIntId());
        return new Receipt();
    }
}
