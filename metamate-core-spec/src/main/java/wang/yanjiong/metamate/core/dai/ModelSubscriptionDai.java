package wang.yanjiong.metamate.core.dai;

import lombok.Data;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    void save(ModelSubscription modelSubscription);

    void deleteById(String id);

    @Data
    class ModelSubscription {
        private String id;

        private String providerId;

        private String extId;

        private String publication;

        private String version;

        private String subscriberId;

        private String group;

        private String name;

        private String tree;

        private String operatorId;
    }
}
