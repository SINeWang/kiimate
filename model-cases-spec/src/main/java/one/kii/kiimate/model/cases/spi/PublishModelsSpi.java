package one.kii.kiimate.model.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.Panic;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface PublishModelsSpi {

    String NAME_ROOT = "root";

    <T> Receipt commit(Form<T> form) throws Panic;

    @Data
    class Form<T> {
        String ownerId;
        String group;
        Class<T> klass;
    }

    @Data
    class Receipt {

        String extId;

        List<String> ints;

        Map<String, Receipt> refs;
    }
}
