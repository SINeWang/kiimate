package one.kii.kiimate.model.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.*;

/**
 * Created by WangYanJiong on 09/04/2017.
 */
public interface SubscribeModelsSpi {

    Receipt commit(Form form) throws Panic, BadRequest, Forbidden, Conflict, NotFound;

    @Data
    class Form {
        String subscriberId;
        String group;
        String name;
        Long set;
    }

    @Data
    class Receipt {
        private String id;
        private String set;
        private String subscriberId;
        private String group;
        private String name;
        private String tree;
        private String operatorId;
    }


}
