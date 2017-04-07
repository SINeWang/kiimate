package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnSubscribeModelExtractor {

    String NAME_ROOT = "root";

    String TREE_MASTER = "master";

    String hashId(String pubExtId, String subscriberId);

    ModelSubscription extract(SubscribeModelApi.Form form, String pubSetHash, String pubExtId, String subscriberId, String operatorId);


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
    }
}
