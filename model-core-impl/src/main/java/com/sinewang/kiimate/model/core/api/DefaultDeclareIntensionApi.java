package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnIntensionExtractor;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
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
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic {
        IntensionDai.Record record = anIntensionExtractor.extract(context, form);

        intensionDai.remember(record);

        IntensionDai.ChannelLatestExtension channel = new IntensionDai.ChannelLatestExtension();
        channel.setId(form.getExtId());

        List<IntensionDai.Record> recordList = intensionDai.load(channel);
        List<Intension> intensions = ValueMapping.from(Intension.class, recordList);

        Receipt receipt = new Receipt();
        receipt.setIntensions(intensions);
        receipt.setSchema(modelRestorer.restoreAsMetaData(channel));
        return receipt;

    }

}
