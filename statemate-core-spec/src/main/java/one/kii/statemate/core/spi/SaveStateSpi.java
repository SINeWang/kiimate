package one.kii.statemate.core.spi;

import lombok.Data;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface SaveStateSpi {


    <T> void save(Form<T> form);

    @Data
    class Form<T> {
        String ownerId;
        String group;
        String name;
        T object;
    }

    @Data
    class Receipt {

    }
}
