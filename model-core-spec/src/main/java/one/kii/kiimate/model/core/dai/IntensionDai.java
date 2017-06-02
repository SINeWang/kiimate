package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.io.annotations.MustHave;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface IntensionDai {


    @Transactional
    void remember(Record record) throws IntensionDuplicated;

    List<Record> load(ChannelLatestExtension channel);

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

        @MustHave
        private Long id;

        @MustHave
        private String commit;

        @MustHave
        private Long extId;

        @MustHave
        private String field;

        @MustHave
        private Boolean single;

        @MustHave
        private String structure;

        private Long refPubSet;

        @MustHave
        private String visibility;

        @MustHave
        private Boolean required;
    }

    class IntensionDuplicated extends Exception {

        @Getter
        private Long intId;

        public IntensionDuplicated(Long intId) {
            this.intId = intId;
        }
    }
}
