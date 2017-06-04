package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublishModelApi implements PublishModelApi {

    @Autowired
    private AnPublicationExtractor publicationExtractor;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelPublicationDai modelPublicationDai;


    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic {


        IntensionDai.ChannelLatestExtension latest = new IntensionDai.ChannelLatestExtension();
        latest.setId(form.getExtId());

        List<IntensionDai.Record> allRecords = new ArrayList<>();

        List<IntensionDai.Record> records = intensionDai.load(latest);
        allRecords.addAll(records);

        List<ModelPublicationDai.Record> publications = publicationExtractor.extract(context, form, records);

        try {
            modelPublicationDai.save(publications);
        } catch (ModelPublicationDai.DuplicatedPublication duplicatedPublication) {
            throw new Conflict(KeyFactorTools.find(Form.class));
        }

        Receipt receipt = ValueMapping.from(Receipt.class, form, context);

        List<Intension> snapshotIntensions = ValueMapping.from(Intension.class, allRecords);

        receipt.setIntensions(snapshotIntensions);

        return receipt;

    }

}
