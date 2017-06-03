package one.kii.kiimate.status.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface StatusDai {

    @Transactional
    Date save(Record record);

    List<Statuses> query(ClueGroup clue);

    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

    @Data
    class Record {

        Long pubSet;

        List<Entry> entries;

        AssetDai.Asset previous;

        String operatorId;
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
