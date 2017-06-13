package one.kii.kiimate.status.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.*;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface RefreshStatusSpi {


    String TREE_MASTER = "master";

    <T> void commit(NameForm<T> form) throws Panic, Conflict, BadRequest, NotFound, Forbidden;

    <T> void commit(IdForm<T> form) throws Panic, Conflict, BadRequest, NotFound, Forbidden;

    @Data
    class NameForm<T> {
        String ownerId;
        String group;
        String name;
        String tree = TREE_MASTER;
        T object;
    }

    @Data
    class IdForm<T> {
        String ownerId;
        String subId;
        T object;
    }

}
