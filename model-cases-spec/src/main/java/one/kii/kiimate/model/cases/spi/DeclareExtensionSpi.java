package one.kii.kiimate.model.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface DeclareExtensionSpi {

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";


    Receipt commit(Form form) throws Panic;

    @Data
    class Form {

        private String ownerId;

        private String group;

        private String name;

        private String tree = TREE_MASTER;

        private String visibility = VISIBILITY_PUBLIC;

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
