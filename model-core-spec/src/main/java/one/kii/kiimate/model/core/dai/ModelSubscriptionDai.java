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

    String getLatestRootExtIdByOwnerSubscription(String owner, String subId);

    List<ModelSubscription> querySubscriptionsByOwnerGroup(String ownerId, String group);

    int countModelSubscriptions(String pubSet);

    List<Subscribers> querySubscriberId(String subscriberId);

    @Data
    class Subscribers {
        String id;
    }

    @Data
    class ModelSubscription {
        private String id;

        private String subSet;

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

        @Getter
        private String tree;

        public DuplicatedSubscription(String subSetHash, String subscriberId, String group, String name, String tree) {
            super();
            this.subSetHash = subSetHash;
            this.subscriberId = subscriberId;
            this.group = group;
            this.name = name;
            this.tree = tree;
        }

    }
}
