package com.sinewang.kiimate.model.core.fui;

import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.fui.AnPublicationExtractor;
import one.kii.kiimate.model.core.fui.AnPublicationValidator;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublicationExtrator implements AnPublicationExtractor {


    @Override
    public Publication extractSnapshot(PublishModelApi.Form form, String extId, String operatorId, Date date) throws MissingParamException {
        Publication publication = new Publication();
        publication.setPubExtId(hashPublishExtId(form.getProviderId(), extId));
        publication.setExtId(extId);
        publication.setOperatorId(operatorId);
        publication.setProviderId(form.getProviderId());
        publication.setVersion(form.getVersion());
        publication.setPublication(AnPublicationValidator.Publication.SNAPSHOT.name());
        publication.setCreatedAt(date);
        return publication;
    }

    @Override
    public String hashId(String pubExitId, String intId) {
        return HashTools.hashHex(pubExitId, intId);
    }

    @Override
    public String hashPublishExtId(String providerId, String extId) {
        return HashTools.hashHex(providerId, extId);
    }

}
