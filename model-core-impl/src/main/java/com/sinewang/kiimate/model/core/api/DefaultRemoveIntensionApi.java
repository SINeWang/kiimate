package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.RemoveIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 09/05/2017.
 */
@Component
public class DefaultRemoveIntensionApi implements RemoveIntensionApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnModelRestorer modelRestorer;

    @Override
    public Receipt commit(WriteContext context, Form form) throws Conflict {

        Receipt receipt = new Receipt();

        intensionDai.removeIntension(form.getIntId());

        List<Intension> intensions = DataTools.copy(intensionDai.selectIntensionsByExtId(form.getExtId()), Intension.class);

        receipt.setIntensions(intensions);

        Map<String, Object> schema = modelRestorer.restoreAsMetaData(form.getExtId());
        receipt.setSchema(schema);

        return receipt;
    }
}
