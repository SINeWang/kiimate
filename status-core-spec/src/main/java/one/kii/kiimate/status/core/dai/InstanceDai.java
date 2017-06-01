package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface InstanceDai {

    @Transactional
    void insert(List<Record> records) throws InstanceDuplicated;

    List<Instance> selectLatestInstanceBySubId(long subId);

    List<Instance> selectInstanceByPubSet(long pubSet);

    @Data
    class Instance {

        private long id;

        private String ownerId;

        private long subId;

        private long extId;

        private long intId;

        private String field;

        private String value;

        private long valueSet;

        private String valueRefPath;

        private String valueRefPolicy;

        private String operatorId;

        private Date beginTime;

    }

    @Data
    class Record {

        @KeyFactor
        private long id;

        private String ownerId;

        private long subId;

        private long extId;

        private long intId;

        private String field;

        private String[] values;

        private long valueSet;

        private String valueRefId;

        private String operatorId;

    }

    class InstanceDuplicated extends Exception {
        public InstanceDuplicated(String message, Throwable e) {
            super(message, e);
        }
    }

}
