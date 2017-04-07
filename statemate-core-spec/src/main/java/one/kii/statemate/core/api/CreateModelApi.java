package one.kii.statemate.core.api;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface CreateModelApi {

    String NAME_ROOT = "ROOT";

    <T> Receipt createModel(String group, Class<T> klass);

    @Data
    class Receipt {

        String extId;

        List<String> ints;

        Map<String, Receipt> refs;
    }
}
