package one.kii.kiimate.status.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface InstanceDai {

    @Transactional
    void insertInstances(List<Instances> instances) throws InstanceDuplicated;

    List<Instance> selectLatestInstanceBySubId(String subId);


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

        private String valueRefId;

        private String operatorId;

    }

    @Data
    class Instances {

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
