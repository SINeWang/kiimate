package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.CommitApi;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 3/23/17.
 */

public interface DeclareExtensionApi extends CommitApi<DeclareExtensionApi.CommitReceipt, WriteContext, DeclareExtensionApi.CommitForm> {

    String TREE_MASTER = "master";

    CommitReceipt commit(WriteContext context, CommitForm form) throws BadRequest, Conflict, NotFound, Panic;

    @Data
    class CommitForm {

        private String group;

        private String name;

        private String tree = TREE_MASTER;

        private String visibility;
    }

    @Data
    class CommitReceipt {

        private String id;

        private String commit;

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }


}
