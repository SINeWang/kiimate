package one.kii.kiimate.model.cases.spi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface DeclareIntensionSpi {

    String VISIBILITY_PUBLIC = "public";

    String commit(PrimitiveIntensionForm form) throws Panic;

    String commit(ImportIntensionForm form) throws Panic;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class PrimitiveIntensionForm {

        private String ownerId;

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String visibility = VISIBILITY_PUBLIC;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class ImportIntensionForm extends PrimitiveIntensionForm {

        private String refExtId;
    }


    @Data
    @EqualsAndHashCode(callSuper = false)
    class IntensionReceipt {

        private String id;

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

    }

}
