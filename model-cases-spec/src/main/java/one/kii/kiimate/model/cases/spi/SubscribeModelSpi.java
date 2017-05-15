package one.kii.kiimate.model.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 09/04/2017.
 */
public interface SubscribeModelSpi {

    Receipt commit(Form form) throws Panic;

    @Data
    class Form {
        String subscriberId;
        String group;
        String name;
        String pubSet;
    }

    @Data
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
