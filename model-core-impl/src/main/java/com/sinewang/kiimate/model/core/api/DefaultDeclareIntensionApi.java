package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnIntensionExtractor;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultDeclareIntensionApi implements DeclareIntensionApi {


    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnIntensionExtractor anIntensionExtractor;

    @Autowired
    private AnModelRestorer modelRestorer;

    @Override
    public Receipt commit(WriteContext context, Form form) throws Conflict {

        AnIntensionExtractor.Intension intension = anIntensionExtractor.parseForm(form);
        if (form.getExtId().equals(form.getRefExtId())) {
            throw new Conflict(new String[]{"extId", "refExtId"});
        }
        IntensionDai.Intension daiRecord = ValueMapping.from(IntensionDai.Intension.class, intension);
        try {
            intensionDai.insertIntension(daiRecord);
        } catch (IntensionDai.IntensionDuplicated extensionDuplicated) {
            throw new Conflict(daiRecord.getId());
        }

        IntensionDai.ChannelExtension channel = new IntensionDai.ChannelExtension();
        channel.setId(form.getExtId());

        List<IntensionDai.Intension> intensionList = intensionDai.loadLatestIntensions(channel);
        List<Intension> intensions = ValueMapping.from(Intension.class, intensionList);

        Receipt receipt = new Receipt();
        receipt.setIntensions(intensions);
        receipt.setSchema(modelRestorer.restoreAsMetaData(form.getExtId()));
        return receipt;

    }

}
