package one.kii.kiimate.model.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.Commit;
import one.kii.summer.beans.annotations.Unique;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface ExtensionDai {

    Record loadLast(ChannelName channel) throws Panic, BadRequest;

    Record loadLast(ChannelId channel) throws Panic, BadRequest;

    Record loadLast(ChannelSet channel) throws Panic, BadRequest;

    List<Record> search(ClueGroup clue) throws BadRequest, Panic;

    @Transactional
    void remember(Record record) throws Conflict, BadRequest;

    void forget(Long id);

    @Data
    class ClueGroup {

        String ownerId;

        String group;
    }


    @Data
    class ChannelId {

        Long id;

        @MayHave
        Date beginTime;
    }

    @Data
    class ChannelSet {
        Long set;

        @MayHave
        Date beginTime;
    }

    @Data
    class ChannelName {

        String ownerId;

        String group;

        String name;

        String tree;

        @MayHave
        Date beginTime;
    }

    @Data
    class Record {

        private Long id;

        private String commit;

        @Unique
        @Commit
        private String ownerId;

        @Unique
        @Commit
        private String group;

        @Unique
        @Commit
        private String name;

        @Unique
        @Commit
        private String tree;

        @Commit
        private String visibility;

        @Commit
        private String operatorId;

        @Commit
        private Date beginTime;

        @MayHave
        @Commit
        @Unique
        private Date endTime;

    }

}
