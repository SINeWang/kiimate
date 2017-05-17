package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.summer.codec.annotations.HashFactor;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnIntensionExtractor {

    Intension parseForm(DeclareIntensionApi.Form form);

    void hashId(Intension intension);

    @Data
    class Intension {

        private String id;

        @HashFactor
        private String extId;

        @HashFactor
        private String field;

        private String visibility;

        private String structure;

        private String refExtId;

        private boolean required;

        private boolean single;
    }
}
