package wang.yanjiong.metamate.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    @Transactional
    void save(List<ModelSubscription> modelSubscriptions);

    ModelSubscription getLatestSubscriptionBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree);

    void deleteById(String id);

    @Data
    class ModelSubscription {
        private String id;

        private String pubSetHash;

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
