package one.kii.kiimate.status.core.dai;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface StatusDai {


    List<Statuses> query(ClueGroup clue);

    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }


    @Data
    class Statuses {

        Long id;

        Long subSet;

        String ownerId;

        String group;

        String name;

        String tree;

        Date beginTime;
    }


    @Data
    class Entry {
        Long id;

        String providerId;

        Long subId;

        Long insId;

        String version;

        String visibility;

        String stability;

    }


}
