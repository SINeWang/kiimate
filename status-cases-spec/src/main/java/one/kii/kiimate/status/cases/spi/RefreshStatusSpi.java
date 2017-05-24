package one.kii.kiimate.status.cases.spi;

import lombok.Data;
import one.kii.summer.io.exception.*;

/**
 * Created by WangYanJiong on 4/7/17.
 */
public interface RefreshStatusSpi {


    String TREE_MASTER = "master";

    <T> void commit(GroupNameTreeForm<T> form) throws Panic, Conflict, BadRequest, NotFound, Forbidden;

    <T> void commit(SubIdForm<T> form) throws Panic, Conflict, BadRequest, NotFound, Forbidden;

    @Data
    class GroupNameTreeForm<T> {
        String ownerId;
        String group;
        String name;
        String tree = TREE_MASTER;
        T object;
    }

    @Data
    class SubIdForm<T> {
        String ownerId;
        String subId;
        T object;
    }

}
