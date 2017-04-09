package wang.yanjiong.metamate.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    @Transactional
    void save(ModelSubscription modelSubscription);

    String getLatestRootExtIdBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree);

    String getLatestSubIdBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree);

    void deleteById(String id);

    @Data
    class ModelSubscription {
        private String id;

        private String subSetHash;

        private String subscriberId;

        private String group;

        private String name;

        private String tree;

        private String operatorId;

    }
}
