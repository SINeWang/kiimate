package one.kii.kiimate.model.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.CommitFactor;
import one.kii.summer.beans.annotations.ConflictFactor;
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

    List<Record> load(ChannelLatestExtension channel) throws BadRequest, Panic;

    List<Record> loadLast(ChannelLastExtension channel) throws BadRequest, Panic;

    List<Record> loadLast(ChannelPubSet channel) throws BadRequest, Panic;

    void forget(ChannelId channel) throws BadRequest;


    @Data
    class ChannelId {
        Long id;
    }

    @Data
    class ChannelLatestExtension {

        Long id;

    }

    @Data
    class ChannelLastExtension {

        Long id;

        @MayHave
        Date beginTime;
    }

    @Data
    class ChannelPubSet {

        Long extId;

        Long pubSet;

        Date beginTime;

    }


    @Data
    class Record {

        private Long id;

        private String commit;

        @CommitFactor
        @ConflictFactor
        private Long extId;

        @CommitFactor
        @ConflictFactor
        private String field;

        @CommitFactor
        @ConflictFactor
        private Boolean single;

        @CommitFactor
        @ConflictFactor
        private String structure;

        @CommitFactor
        @MayHave
        @ConflictFactor
        private Long refPubSet;

        @CommitFactor
        @ConflictFactor
        private String visibility;

        @CommitFactor
        @ConflictFactor
        private Boolean required;

        @CommitFactor
        @ConflictFactor
        private String operatorId;

        @CommitFactor
        private Date beginTime;

        @CommitFactor
        @MayHave
        @ConflictFactor
        private Date endTime;
    }

}
