package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.summer.beans.annotations.KeyFactor;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnIntensionExtractor {

    Intension parseForm(DeclareIntensionApi.Form form);

    @Data
    class Intension {

        private long id;

        @KeyFactor
        private long extId;

        @KeyFactor
        private String field;

        private String visibility;

        private String structure;

        private long refPubSet;

        private boolean required;

        private boolean single;
    }
}
