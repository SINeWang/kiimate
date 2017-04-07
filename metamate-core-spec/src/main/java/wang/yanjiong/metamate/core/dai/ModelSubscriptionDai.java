package wang.yanjiong.metamate.core.dai;

import lombok.Data;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    void save(ModelSubscription modelSubscription);

    ModelSubscription getLatestSubscriptionBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree);

    void deleteById(String id);

    @Data
    class ModelSubscription {
        private String id;

        private String pubExtId;

        private String subscriberId;

        private String group;

        private String name;

        private String tree;

        private String operatorId;

        // join fields
        private String extId;
    }
}
