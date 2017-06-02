package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.annotations.MustHave;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface InstanceDai {

    @Transactional
    void remember(List<Record> records) throws InstanceDuplicated;

    List<Instance> loadInstances(ChannelModelSubId channel);

    List<Instance> loadInstances(ChannelStatusPubSet channel);

    @Data
    class ChannelStatusPubSet {
        Long pubSet;
    }

    @Data
    class ChannelModelSubId {
        Long subId;
    }

    @Data
    class Instance {

        private Long id;

        private String commit;

        private String ownerId;

        private Long subId;

        private Long extId;

        private Long intId;

        private String field;

        private String value;

        private Long valueSet;

        private String valueRefPath;

        private String valueRefPolicy;

        private String operatorId;

        private Date beginTime;

    }

    @Data
    class Record {

        @KeyFactor
        private Long id;

        @MustHave
        private String commit;

        private String ownerId;

        private Long subId;

        private Long extId;

        private Long intId;

        private String field;

        private String[] values;

        private Long valueSet;

        private String valueRefId;

        private String operatorId;

    }

    class InstanceDuplicated extends Exception {
        public InstanceDuplicated(String message, Throwable e) {
            super(message, e);
        }
    }

}
