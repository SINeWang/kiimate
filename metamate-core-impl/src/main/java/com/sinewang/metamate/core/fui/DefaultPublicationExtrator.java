package com.sinewang.metamate.core.fui;

import one.kii.summer.codec.utils.HashTools;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.api.SnapshotModelApi;
import wang.yanjiong.metamate.core.fui.AnPublicationExtractor;
import wang.yanjiong.metamate.core.fui.AnPublicationValidator;

import java.util.Date;

/**
 * Created by WangYanJiong on 05/04/2017.
 */

@Component
public class DefaultPublicationExtrator implements AnPublicationExtractor {


    @Override
    public Publication extractSnapshot(SnapshotModelApi.Form form, String extId, String operatorId, Date date) throws MissingParamException {
        Publication publication = new Publication();
        publication.setPubExtId(hashPubExtId(form.getProviderId(), extId, AnPublicationValidator.Publication.SNAPSHOT.name(), form.getVersion()));
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
    public String hashPubExtId(String providerId, String extId, String publication, String version) {
        return HashTools.hashHex(providerId, extId, publication, version);
    }

}
