package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.fui.AnSubscribeModelExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultSubscribeModelsApi implements SubscribeModelsApi {

    @Autowired
    private AnSubscribeModelExtractor subscribeModelExtractor;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws Conflict {

        AnSubscribeModelExtractor.ModelSubscription modelSubscription = subscribeModelExtractor.extract(
                form, context);

        ModelSubscriptionDai.ModelSubscription subscription = ValueMapping.from(ModelSubscriptionDai.ModelSubscription.class, modelSubscription);

        try {
            modelSubscriptionDai.save(subscription);
            return ValueMapping.from(Receipt.class, modelSubscription);
        } catch (ModelSubscriptionDai.DuplicatedSubscription duplicatedSubscription) {
            throw new Conflict(subscription.getId());
        }
    }


}
