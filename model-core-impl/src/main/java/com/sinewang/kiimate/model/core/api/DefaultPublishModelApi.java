package com.sinewang.kiimate.model.core.api;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublishModelApi implements PublishModelApi {

    private static final Eid64Generator setgen = new Eid64Generator(3);
    @Autowired
    private AnPublicationExtractor publicationExtractor;
    @Autowired
    private IntensionDai intensionDai;
    @Autowired
    private ModelPublicationDai modelPublicationDai;

    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic {


        IntensionDai.ChannelLatestExtension latest = new IntensionDai.ChannelLatestExtension();
        latest.setId(form.getExtId());

        List<IntensionDai.Record> records = intensionDai.load(latest);

        ModelPublicationDai.ChannelPubSet pubSet = new ModelPublicationDai.ChannelPubSet();
        pubSet.setPubSet(setgen.born());

        List<ModelPublicationDai.Record> publications = publicationExtractor.extract(context, form, records, pubSet);

        modelPublicationDai.save(publications, form);

        Receipt receipt = ValueMapping.from(Receipt.class, form, context, pubSet);

        List<Intension> intensions = ValueMapping.from(Intension.class, records);

        receipt.setIntensions(intensions);

        return NotBadResponse.of(Receipt.class, MayHave.class, receipt);

    }

}
