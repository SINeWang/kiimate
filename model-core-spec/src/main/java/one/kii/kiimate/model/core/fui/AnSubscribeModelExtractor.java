package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.summer.io.context.WriteContext;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public interface AnSubscribeModelExtractor {

    ModelSubscription extract(SubscribeModelsApi.Form form, WriteContext context);

    @Data
    class ModelSubscription {

        private Long id;

        private Long subSet;

        private String subscriberId;

        private String group;

        private String name;

        private String tree;

        private String operatorId;
    }
}
