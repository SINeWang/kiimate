package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.asdf.api.CommitApi;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface SubscribeModelsApi extends CommitApi<SubscribeModelsApi.Receipt, WriteContext, SubscribeModelsApi.Form> {

    String TREE_MASTER = "master";

    Receipt commit(WriteContext context, Form form) throws Conflict, Panic;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {
        private Long subSet;
        private String group;
        private String name;
        private String tree = TREE_MASTER;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {
        private String id;
        private String subSet;
        private String subscriberId;
        private String group;
        private String name;
        private String tree;
        private String operatorId;
    }

}
