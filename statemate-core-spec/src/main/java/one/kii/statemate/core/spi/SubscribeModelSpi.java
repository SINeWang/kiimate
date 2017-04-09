package one.kii.statemate.core.spi;

import lombok.Data;

/**
 * Created by WangYanJiong on 09/04/2017.
 */
public interface SubscribeModelSpi {

    Receipt subscribe(Form form);

    @Data
    class Form {
        String group;
        String pubSetHash;
    }

    @Data
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
