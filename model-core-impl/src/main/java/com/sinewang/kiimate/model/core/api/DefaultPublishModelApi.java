package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnModelPubExtractFui;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.txdid.txd64.T1Did64Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublishModelApi implements PublishModelApi {

    private static final T1Did64Generator setgen = new T1Did64Generator(3);
    @Autowired
    private AnModelPubExtractFui publicationExtractor;
    @Autowired
    private IntensionDai intensionDai;
    @Autowired
    private ModelPublicationDai modelPublicationDai;

    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic {


        IntensionDai.ChannelExtensionId channel = new IntensionDai.ChannelExtensionId();
        channel.setId(form.getExtId());

        List<IntensionDai.Record> records = intensionDai.loadLast(channel);

        ModelPublicationDai.ChannelSet pubSet = new ModelPublicationDai.ChannelSet();
        pubSet.setSet(String.valueOf(setgen.born()));

        List<ModelPublicationDai.Record> publications = publicationExtractor.extract(context, form, records, pubSet);

        modelPublicationDai.save(publications, form);

        Receipt receipt = ValueMapping.from(Receipt.class, form, context, pubSet);

        List<Intension> intensions = ValueMapping.from(Intension.class, records);

        receipt.setIntensions(intensions);

        return NotBadResponse.of(receipt);

    }

}
