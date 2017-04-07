package one.kii.statemate.core.spi;

import lombok.Data;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface CreateExtensionSpi {

    String createMasterPublicExtension(ExtensionForm form);


    @Data
    class ExtensionForm {

        private String group;

        private String name;

    }

    @Data
    class ExtensionReceipt {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }

}
