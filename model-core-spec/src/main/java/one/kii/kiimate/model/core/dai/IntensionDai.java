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
public interface IntensionDai {


    @Transactional
    void remember(Record record) throws Conflict, BadRequest;

    List<Record> loadLast(ChannelExtensionId channel) throws BadRequest, Panic;

    List<Record> loadLast(ChannelPubSet channel) throws BadRequest, Panic;

    void forget(ChannelId channel) throws BadRequest;


    @Data
    class ChannelId {
        Long id;
    }

    @Data
    class ChannelExtensionId {

        Long id;

        @MayHave
        Date beginTime;

        @MayHave
        Date endTime;
    }

    @Data
    class ChannelPubSet {

        Long set;

    }


    @Data
    class Record {

        private Long id;

        private String commit;

        @Unique
        @Commit
        private Long extId;

        @Unique
        @Commit
        private String field;

        @Unique
        @Commit
        private Boolean single;

        @Unique
        @Commit
        private String structure;

        @Unique
        @Commit
        @MayHave
        private Long refSet;

        @Unique
        @Commit
        private String visibility;

        @Unique
        @Commit
        private Boolean required;

        @Commit
        private String operatorId;

        @Commit
        private Date beginTime;

        @Unique
        @Commit
        @MayHave
        private Date endTime;
    }

}
