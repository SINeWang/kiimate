package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitExtensionApi extends VisitApi<VisitExtensionApi.Receipt, ReadContext, VisitExtensionApi.Form> {

    String NAME_ROOT = "root";

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";

    Receipt visit(ReadContext context, Form form) throws BadRequest, NotFound, Panic;

    @Data
    class Form {
        String group;
        String name = NAME_ROOT;
        String tree = TREE_MASTER;
        String visibility = VISIBILITY_PUBLIC;
    }

    @Data
    class Receipt {
        String id;
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

        private Boolean single;

        private String structure;

        private String refPubSet;

        private String visibility;

        private Boolean required;
    }

}
