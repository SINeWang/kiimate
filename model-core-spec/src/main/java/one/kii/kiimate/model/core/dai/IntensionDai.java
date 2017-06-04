package one.kii.kiimate.model.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface IntensionDai {


    @Transactional
    void remember(Record record) throws Conflict;

    List<Record> load(ChannelLatestExtension channel) throws BadRequest;

    List<Record> loadLast(ChannelLastExtension channel);

    List<Record> loadLast(ChannelPubSet channel);

    void forget(ChannelId channel);


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

        @KeyFactor
        private Long extId;

        @KeyFactor
        private String field;

        @KeyFactor
        private Boolean single;

        @KeyFactor
        private String structure;

        @KeyFactor
        @MayHave
        private Long refPubSet;

        @KeyFactor
        private String visibility;

        @KeyFactor
        private Boolean required;

        @KeyFactor
        private String operatorId;

        @KeyFactor
        private Date beginTime;

        @KeyFactor
        @MayHave
        private Date endTime;
    }

}
