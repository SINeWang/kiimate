package one.kii.statemate.core.spi;

import lombok.Data;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface CreateExtensionSpi {

    Receipt createMasterPublicExtension(Form form);

    @Data
    class Form {

        private String group;

        private String name;

    }

    @Data
    class Receipt {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }

}
