package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnIntensionExtractor;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultDeclareIntensionApi implements DeclareIntensionApi {


    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnIntensionExtractor anIntensionExtractor;

    @Override
    public Receipt declareIntension(WriteContext context, Form form) throws Conflict {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parseForm(form);
        if (form.getExtId().equals(form.getRefExtId())) {
            throw new Conflict(new String[]{"extId", "refExtId"});
        }
        IntensionDai.Intension daiRecord = DataTools.copy(intension, IntensionDai.Intension.class);
        try {
            intensionDai.insertIntension(daiRecord);
            return DataTools.copy(daiRecord, Receipt.class);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            throw new Conflict(daiRecord.getId());
        }

    }

}
