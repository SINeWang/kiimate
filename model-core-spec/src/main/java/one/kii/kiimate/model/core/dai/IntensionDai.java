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

    List<Record> loadLatest(ChannelExtension channel);

    List<Record> loadLast(ChannelPubSet channel);

    void forget(ChannelId channel);


    @Data
    class ChannelId {
        long id;
    }

    @Data
    class ChannelExtension {

        long id;

        Date beginTime;
    }

    @Data
    class ChannelPubSet {

        long extId;

        long pubSet;

        Date beginTime;

    }


    @Data
    class Record {

        @MustHave
        private long id;

        @MustHave
        private String commit;

        @MustHave
        private long extId;

        @MustHave
        private String field;

        @MustHave
        private boolean single;

        @MustHave
        private String structure;

        private long refPubSet;

        @MustHave
        private String visibility;

        @MustHave
        private boolean required;
    }

    class IntensionDuplicated extends Exception {

        @Getter
        private long intId;

        public IntensionDuplicated(long intId) {
            this.intId = intId;
        }
    }
}
