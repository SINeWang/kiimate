package com.sinewang.metamate.core.fi;

import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.api.SnapshotModelApi;
import wang.yanjiong.metamate.core.fi.AnPublicationExtractor;
import wang.yanjiong.metamate.core.fi.AnPublicationValidator;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Component
public class DefaultPublicationExtrator implements AnPublicationExtractor {


    @Override
    public Publication extractSnapshot(SnapshotModelApi.SnapshotModelForm form, String ownerId, String extId, String operatorId) throws MissingParamException {
        Publication publication = new Publication();
        publication.setExtId(extId);
        publication.setOperatorId(operatorId);
        publication.setOwnerId(ownerId);
        publication.setVersion(form.getVersion());
        publication.setPublication(AnPublicationValidator.Publication.SNAPSHOT.name());
        return publication;
    }

    @Override
    public Publication extractRelease(SnapshotModelApi.SnapshotModelForm form, String ownerId, String extId, String operatorId) throws MissingParamException {
        Publication publication = new Publication();
        publication.setExtId(extId);
        publication.setOperatorId(operatorId);
        publication.setOwnerId(ownerId);
        publication.setVersion(form.getVersion());
        publication.setPublication(AnPublicationValidator.Publication.RELEASE.name());
        return publication;
    }

}
