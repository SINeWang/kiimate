package com.sinewang.kiimate.model.core.fui;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.exception.BadRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublicationExtrator implements AnPublicationExtractor {

    private static final Eid64Generator idgen = new Eid64Generator(2);

    private static final Eid64Generator setgen = new Eid64Generator(3);

    @Override
    public ExtensionPublication extract(PublishModelApi.Form form, long extId, String operatorId, Date date) throws BadRequest {
        ExtensionPublication extensionPublication = ValueMapping.from(ExtensionPublication.class, form);
        extensionPublication.setExtId(extId);
        extensionPublication.setOperatorId(operatorId);
        extensionPublication.setBeginTime(date);
        return extensionPublication;
    }

    @Override
    public List<IntensionPublication> extract(ExtensionPublication extension, List<IntensionDai.Record> records) {

        List<IntensionPublication> publications = new ArrayList<>();

        List<String> ids = new ArrayList<>();
        for (IntensionDai.Record record : records) {
            IntensionPublication publication = ValueMapping.from(IntensionPublication.class, record, extension);
            publication.setIntId(record.getId());
            String id = HashTools.hashHex(publication);
            publication.setId(idgen.born());
            ids.add(id);
            publications.add(publication);
        }

        long pubSet = setgen.born();

        for (IntensionPublication publication : publications) {
            publication.setPubSet(pubSet);
        }
        return publications;
    }

}
