package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.context.exception.BadRequest;
import one.kii.summer.context.exception.Conflict;

/**
 * Created by WangYanJiong on 3/23/17.
 */

public interface DeclareExtensionApi {


    Receipt declareExtension(Form form) throws BadRequest, Conflict;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        String requestId;

        String operatorId;

        String ownerId;

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }

}
