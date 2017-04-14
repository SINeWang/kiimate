package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;

/**
 * Created by WangYanJiong on 3/23/17.
 */

public interface DeclareExtensionApi {


    Receipt declareExtension(WriteContext context, Form form) throws BadRequest, Conflict;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

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
