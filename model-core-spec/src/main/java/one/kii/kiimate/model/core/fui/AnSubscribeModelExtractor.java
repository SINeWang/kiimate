package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.SubscribeModelApi;
import one.kii.summer.beans.annotations.KeyFactor;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnSubscribeModelExtractor {

    ModelSubscription extract(SubscribeModelApi.Form form, String subscriberId, String operatorId);

    @Data
    class ModelSubscription {

        private String id;

        @KeyFactor
        private String subSet;

        @KeyFactor
        private String subscriberId;

        @KeyFactor
        private String group;

        @KeyFactor
        private String name;

        @KeyFactor
        private String tree;

        private String operatorId;
    }
}
