package one.kii.kiimate.model.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.annotations.MustHave;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface ExtensionDai {

    Record loadLast(ChannelCoordinate channel) throws NotFound;

    Record loadLast(ChannelId channel) throws NotFound;

    List<Record> search(ClueGroup clue);

    @Transactional
    void remember(Record record) throws Conflict;

    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

    @Data
    class ChannelId {
        @MustHave
        Long id;
        @MustHave
        Date beginTime;
    }

    @Data
    class ChannelCoordinate {
        @MustHave
        String ownerId;
        @MustHave
        String group;
        @MustHave
        String name;
        @MustHave
        String tree;
        @MustHave
        Date beginTime;
    }

    @Data
    class Record {

        private Long id;

        private String commit;

        @KeyFactor
        private String ownerId;

        @KeyFactor
        private String group;

        @KeyFactor
        private String name;

        @KeyFactor
        private String tree;

        @KeyFactor
        private String visibility;

        @KeyFactor
        private String operatorId;

        @KeyFactor
        private Date beginTime;

        @MayHave
        @KeyFactor
        private Date endTime;

    }

}
