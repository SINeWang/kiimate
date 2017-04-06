package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnSubscribeModelExtractor {

    String hashId(String providerId, String extId, String publication, String version, String subscriberId);

    ModelSubscription extract(SubscribeModelApi.Form form, String providerId, String extId, String publication, String version, String subscriberId, String operatorId);


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
