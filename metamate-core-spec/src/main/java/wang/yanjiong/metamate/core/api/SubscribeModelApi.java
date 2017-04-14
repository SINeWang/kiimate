package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface SubscribeModelApi {

    String TREE_MASTER = "master";


    Receipt subscribe(WriteContext context, Form form) throws Conflict;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {
        private String pubSetHash;
        private String group;
        private String name;
    }


    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {
        private String id;
        private String subSetHash;
        private String subscriberId;
        private String group;
        private String name;
        private String tree;
        private String operatorId;
    }

}
