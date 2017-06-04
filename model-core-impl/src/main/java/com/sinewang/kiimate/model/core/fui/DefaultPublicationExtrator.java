package com.sinewang.kiimate.model.core.fui;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublicationExtrator implements AnPublicationExtractor {

    private static final Eid64Generator idgen = new Eid64Generator(2);

    private static final Eid64Generator setgen = new Eid64Generator(3);

    @Override
    public List<ModelPublicationDai.Record> extract(WriteContext context, PublishModelApi.Form form, List<IntensionDai.Record> records) {

        List<ModelPublicationDai.Record> publications = new ArrayList<>();
        Long pubSet = setgen.born();
        for (IntensionDai.Record record : records) {
            ModelPublicationDai.Record publication = ValueMapping.from(ModelPublicationDai.Record.class, record);
            publication.setIntId(record.getId());
            publication.setId(idgen.born());
            publication.setPubSet(pubSet);
            publications.add(publication);
        }

        return publications;
    }
}
