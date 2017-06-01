package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitExtensionApi {

    String NAME_ROOT = "root";

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";

    Receipt visit(ReadContext context, Form form) throws NotFound;

    @Data
    class Form {
        String group;
        String name = NAME_ROOT;
        String tree = TREE_MASTER;
        String visibility = VISIBILITY_PUBLIC;
    }

    @Data
    class Receipt {
        long id;
        String ownerId;
        String group;
        String name;
        String tree;
        String visibility;
        List<Intension> intensions;
        Map<String, Object> schema;
    }

    @Data
    class Intension {

        private String id;

        private String field;

        private boolean single;

        private String structure;

        private String refPubSet;

        private String visibility;

        private boolean required;
    }

}
