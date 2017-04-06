package com.sinewang.metamate.core.fi;

import one.kii.summer.codec.utils.HashTools;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.api.ReleaseModelApi;
import wang.yanjiong.metamate.core.api.SnapshotModelApi;
import wang.yanjiong.metamate.core.fi.AnPublicationExtractor;
import wang.yanjiong.metamate.core.fi.AnPublicationValidator;

import java.util.Date;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Component
public class DefaultPublicationExtrator implements AnPublicationExtractor {


    @Override
    public Publication extractSnapshot(SnapshotModelApi.Form form, String providerId, String extId, String operatorId) throws MissingParamException {
        Publication publication = new Publication();
        publication.setExtId(extId);
        publication.setOperatorId(operatorId);
        publication.setProviderId(providerId);
        publication.setVersion(form.getVersion());
        publication.setPublication(AnPublicationValidator.Publication.SNAPSHOT.name());
        publication.setCreatedAt(new Date());
        return publication;
    }

    @Override
    public Publication extractRelease(ReleaseModelApi.ReleaseForm form, String providerId, String extId, String operatorId) throws MissingParamException {
        Publication publication = new Publication();
        publication.setExtId(extId);
        publication.setOperatorId(operatorId);
        publication.setProviderId(providerId);
        publication.setVersion(form.getVersion());
        publication.setPublication(AnPublicationValidator.Publication.RELEASE.name());
        publication.setCreatedAt(new Date());
        return publication;
    }

    @Override
    public String hashId(String providerId, String extId, String intId, String version, String publication) {
        return HashTools.hashHex(providerId, extId, intId, version, publication);
    }


}
