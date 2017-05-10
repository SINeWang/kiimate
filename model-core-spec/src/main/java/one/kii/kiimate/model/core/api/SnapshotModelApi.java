package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface SnapshotModelApi {

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";


    Receipt snapshot(WriteContext context, Form form) throws BadRequest, Conflict;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String providerId;

        private String extId;

        private String version;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        List<Intension> intensions;
        private String pubSet;
        private String providerId;

        private String ownerId;

        private String version;

        private Date createdAt;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Intension {

        String field;

        boolean single;
    }

    class RefereceExtensionHasNotBeenPublished extends Exception {

        public RefereceExtensionHasNotBeenPublished(String message) {
            super(message);
        }
    }

}
