package com.sinewang.kiimate.model.core.fui;

import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.exception.BadRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublicationExtrator implements AnPublicationExtractor {


    @Override
    public ExtensionPublication extract(PublishModelApi.Form form, String extId, String operatorId, Date date) throws BadRequest {
        ExtensionPublication extensionPublication = ValueMapping.from(ExtensionPublication.class, form);
        extensionPublication.setExtId(extId);
        extensionPublication.setOperatorId(operatorId);
        extensionPublication.setBeginTime(date);
        return extensionPublication;
    }

    @Override
    public List<IntensionPublication> extract(ExtensionPublication extension, List<IntensionDai.Intension> intensions) {

        List<IntensionPublication> publications = new ArrayList<>();

        List<String> ids = new ArrayList<>();
        for (IntensionDai.Intension intension : intensions) {
            IntensionPublication publication = ValueMapping.from(IntensionPublication.class, extension);
            String id = HashTools.hashHex(publication);
            publication.setIntId(intension.getId());
            publication.setId(id);
            ids.add(id);
            publications.add(publication);
        }

        String[] idArray = ids.toArray(new String[0]);
        Arrays.sort(idArray);
        String pubSet = HashTools.hashHex(idArray);

        for (IntensionPublication publication : publications) {
            publication.setPubSet(pubSet);
        }
        return publications;
    }

}
