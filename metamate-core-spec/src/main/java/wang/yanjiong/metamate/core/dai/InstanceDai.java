package wang.yanjiong.metamate.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface InstanceDai {

    @Transactional
    void insertInstances(List<Instance> instances) throws InstanceDuplicated;


    List<Instance> selectLatestInstancesByOwnerIdExtId(String extId, String ownerId);

    @Data
    class Instance {

        private String id;

        private String extId;

        private String intId;

        private String ownerId;

        private String field;

        private String value;

        private String valueRefId;

        private String operatorId;

    }

    class InstanceDuplicated extends Exception {
        public InstanceDuplicated(String message, Throwable e) {
            super(message, e);
        }
    }

}
