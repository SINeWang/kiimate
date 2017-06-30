package com.sinewang.kiimate.model.core.fui;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnModelPubExtractFui;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultModelPubExtractFui implements AnModelPubExtractFui {

    private static final Eid64Generator idgen = new Eid64Generator(2);


    @Override
    public List<ModelPublicationDai.Record> extract(WriteContext context, PublishModelApi.Form form, List<IntensionDai.Record> instances, ModelPublicationDai.ChannelSet pubSet) throws Panic {
        Date now = new Date();
        List<ModelPublicationDai.Record> publications = new ArrayList<>();
        for (IntensionDai.Record record : instances) {
            ModelPublicationDai.Record publication = ValueMapping.from(ModelPublicationDai.Record.class, pubSet, record, form);
            publication.setIntId(record.getId());
            publication.setId(String.valueOf(idgen.born()));
            publication.setBeginTime(now);
            NotBadResponse.of(publication);
            publications.add(publication);
        }
        return publications;
    }
}
