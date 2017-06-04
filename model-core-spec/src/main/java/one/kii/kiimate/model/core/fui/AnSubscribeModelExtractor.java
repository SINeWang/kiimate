package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.context.WriteContext;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnSubscribeModelExtractor {

    ModelSubscription extract(SubscribeModelsApi.Form form, WriteContext context);

    @Data
    class ModelSubscription {

        private Long id;

        @KeyFactor
        private Long subSet;

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
