package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.ReleaseModelApi;
import wang.yanjiong.metamate.core.api.SnapshotModelApi;

import java.util.Date;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnPublicationExtractor {

    Publication extractSnapshot(SnapshotModelApi.Form form,
                                String providerId,
                                String extId,
                                String operatorId) throws MissingParamException;

    Publication extractRelease(ReleaseModelApi.ReleaseForm form,
                               String providerId,
                               String extId,
                               String operatorId) throws MissingParamException;

    @Data
    class Publication {

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
