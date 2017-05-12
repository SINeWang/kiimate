package one.kii.kiimate.status.open.spi;

import lombok.Data;
import one.kii.summer.io.exception.Panic;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface RefreshStatusSpi {


    <T> void save(Form<T> form) throws Panic;

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
