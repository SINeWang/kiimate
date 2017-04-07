package one.kii.statemate.core.spi;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface CreateIntensionSpi {

    String createPublicPrimitiveIntension(PrimitiveIntensionForm form);

    String createPublicImportIntension(ImportIntensionForm form);

    @Data
    @EqualsAndHashCode(callSuper = false)
    class PrimitiveIntensionForm {

        private String extId;

        private String field;

        private boolean single;

        private String structure;

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
