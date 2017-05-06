package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;

import java.util.List;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitExtensionApi {

    String NAME_ROOT = "root";

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";

    Receipt visit(ReadContext context, Form form);

    Receipt visit(ReadContext context, SimpleForm form);

    Receipt visit(ReadContext context, TinyForm form);

    @Data
    class Form {
        String group;
        String name;
        String tree;
    }

    @Data
    class SimpleForm {
        String group;
        String name;
    }

    @Data
    class TinyForm {
        String group;
    }

    @Data
    class Receipt {
        String ownerId;
        String group;
        String name;
        String tree;
        String extId;
        String visibility;
        List<Intension> intensions;
    }

    @Data
    class Intension {

        private String id;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

        private boolean required;
    }

}
