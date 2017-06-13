package one.kii.kiimate.status.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.*;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface RefreshStatusSpi {


    <T> void commit(IdForm<T> form) throws Panic, Conflict, BadRequest, NotFound, Forbidden;

    @Data
    class IdForm<T> {
        String ownerId;
        Long id;
        T object;
    }

}
