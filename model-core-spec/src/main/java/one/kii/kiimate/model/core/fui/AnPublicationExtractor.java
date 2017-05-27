package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.exception.BadRequest;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnPublicationExtractor {

    ExtensionPublication extract(PublishModelApi.Form form,
                                 String extId,
                                 String operatorId,
                                 Date date) throws BadRequest;

    List<IntensionPublication> extract(
            ExtensionPublication extensionPublication,
            List<IntensionDai.Intension> intensions);

    @Data
    class ExtensionPublication {

        private String providerId;

        private String extId;

        private String stability;

        private String version;

        private String operatorId;

        private Date beginTime;

    }

    @Data
    class IntensionPublication {

        private String id;

        private String pubSet;

        @KeyFactor
        private String providerId;

        @KeyFactor
        private String extId;

        @KeyFactor
        private String intId;

        @KeyFactor
        private String stability;

        @KeyFactor
        private String version;

        private String operatorId;

        private Date beginTime;

    }

}
