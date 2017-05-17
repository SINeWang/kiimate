package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.SubscribeModelApi;
import one.kii.summer.codec.annotations.HashFactor;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnSubscribeModelExtractor {
    
    ModelSubscription extract(SubscribeModelApi.Form form, String subscriberId, String operatorId);

    @Data
    class ModelSubscription {

        private String id;

        @HashFactor
        private String subSet;

        @HashFactor
        private String subscriberId;

        @HashFactor
        private String group;

        @HashFactor
        private String name;

        @HashFactor
        private String tree;

        private String operatorId;
    }
}
