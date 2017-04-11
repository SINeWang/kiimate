package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnSubscribeModelExtractor {

    String TREE_MASTER = "master";

    String hashId(String subscriberId, String pubSetHash, String group, String name, String tree);

    ModelSubscription extract(SubscribeModelApi.Form form, String subscriberId, String operatorId, String tree);

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
