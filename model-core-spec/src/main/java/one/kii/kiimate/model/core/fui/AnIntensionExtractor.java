package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.annotations.MustHave;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnIntensionExtractor {

    Intension extract(DeclareIntensionApi.Form form);

    @Data
    class Intension {

        private long id;

        private String commit;

        @KeyFactor
        @MustHave
        private long extId;

        @KeyFactor
        @MustHave
        private String field;

        @KeyFactor
        @MustHave
        private String visibility;

        @KeyFactor
        private String structure;

        @KeyFactor
        private long refPubSet;

        @KeyFactor
        @MustHave
        private boolean required;

        @KeyFactor
        @MustHave
        private boolean single;
    }
}
