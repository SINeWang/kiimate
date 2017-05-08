package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.SnapshotModelApi;

import java.util.Date;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnPublicationExtractor {

    Publication extractSnapshot(SnapshotModelApi.Form form,
                                String extId,
                                String operatorId,
                                Date date) throws MissingParamException;

    String hashId(String pubExitId, String intId);

    String hashPublishExtId(String providerId, String extId);

    @Data
    class Publication {

        private String pubExtId;

        private String providerId;

        private String extId;

        private String version;

        private String publication;

        private String operatorId;

        private Date createdAt;

    }

    class MissingParamException extends Exception {
        public MissingParamException(String message) {
            super(message);
        }
    }

}
