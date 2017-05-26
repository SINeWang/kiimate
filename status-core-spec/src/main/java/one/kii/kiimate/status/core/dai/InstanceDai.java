package one.kii.kiimate.status.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface InstanceDai {

    @Transactional
    void insert(List<Record> records) throws InstanceDuplicated;

    List<Instance> selectLatestInstanceBySubId(String subId);

    List<Instance> selectInstanceByPubSet(String pubSet);

    @Data
    class LatestStatus {
        Date beginTime;
        List<Instance> instances;
    }


    @Data
    class Instance {

        private String id; // id = hashHex(instances.id, value)

        private String ownerId;

        private String subId;

        private String extId;

        private String intId;

        private String field;

        private String value;

        private String valueSetHash;

        private String valueRefPath;

        private String valueRefPolicy;

        private String operatorId;

        private Date beginTime;

    }

    @Data
    class Record {

        private String id; // id = hashHex(hashHex(extId, fieldId), ownerId)

        private String ownerId;

        private String subId;

        private String extId;

        private String intId;

        private String field;

        private String[] values;

        private String valueSetHash;

        private String valueRefId;

        private String operatorId;

    }

    class InstanceDuplicated extends Exception {
        public InstanceDuplicated(String message, Throwable e) {
            super(message, e);
        }
    }

}
