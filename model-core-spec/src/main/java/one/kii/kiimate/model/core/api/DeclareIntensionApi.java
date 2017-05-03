package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface DeclareIntensionApi {


    Receipt declareIntension(WriteContext context, Form form) throws Conflict;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;


    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        private String id;

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

    }

}
