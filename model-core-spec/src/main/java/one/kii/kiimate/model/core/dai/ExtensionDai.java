package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.io.annotations.MustHave;
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
    void remember(Record record) throws ExtensionDuplicated;

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

        @MustHave
        private Long id;

        @MustHave
        private String commit;

        @MustHave
        private String ownerId;

        @MustHave
        private String group;

        @MustHave
        private String name;

        @MustHave
        private String tree;

        @MustHave
        private String visibility;

        private Date beginTime;

        private Date endTime;

    }

    class ExtensionDuplicated extends Exception {

        @Getter
        private String extId;

        public ExtensionDuplicated(String extId) {
            this.extId = extId;
        }
    }


}
