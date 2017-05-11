package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    @Transactional
    void save(ModelSubscription modelSubscription) throws DuplicatedSubscription;

    String getLatestRootExtIdBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree);

    String getLatestSubIdBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree);

    List<ModelSubscription> querySubscriptionsByOwnerGroup(String ownerId, String group);

    int countModelSubscriptions(String pubSetHash);

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

    class DuplicatedSubscription extends Exception {

        @Getter
        private String subSetHash;
        @Getter
        private String subscriberId;
        @Getter
        private String group;
        @Getter
        private String name;

        public DuplicatedSubscription(String subSetHash, String subscriberId, String group, String name) {
            super();
            this.subSetHash = subSetHash;
            this.subscriberId = subscriberId;
            this.group = group;
            this.name = name;
        }

    }
}
