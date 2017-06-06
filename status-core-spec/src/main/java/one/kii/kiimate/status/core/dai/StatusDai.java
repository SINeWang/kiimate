package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.io.annotations.MayHave;

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

        Long pubSet;

        String providerId;

        String group;

        String name;

        String stability;

        String version;

        Date beginTime;

        @MayHave
        Date endTime;
    }


}
