package one.kii.statemate.core.spi;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface CreateModelSpi {

    String NAME_ROOT = "root";

    <T> Receipt createModel(String group, Class<T> klass);

    @Data
    class Receipt {

        String extId;

        List<String> ints;

        Map<String, Receipt> refs;
    }
}
