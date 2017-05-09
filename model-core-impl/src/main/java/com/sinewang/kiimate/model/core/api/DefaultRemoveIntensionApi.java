package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.RemoveIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 09/05/2017.
 */
@Component
public class DefaultRemoveIntensionApi implements RemoveIntensionApi {

    @Autowired
    private IntensionDai intensionDai;

    @Override
    public List<Intension> removeIntension(WriteContext context, Form form) throws Conflict {

        intensionDai.removeIntension(form.getIntId());

        List<Intension> intensions = DataTools.copy(intensionDai.selectIntensionsByExtId(form.getExtId()), Intension.class);
        return intensions;
    }
}
