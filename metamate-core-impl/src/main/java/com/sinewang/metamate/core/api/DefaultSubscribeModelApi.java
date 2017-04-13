package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.context.exception.Conflict;
import one.kii.summer.context.io.WriteContext;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;
import wang.yanjiong.metamate.core.dai.ModelSubscriptionDai;
import wang.yanjiong.metamate.core.fi.AnSubscribeModelExtractor;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public class DefaultSubscribeModelApi implements SubscribeModelApi {

    @Autowired
    private AnSubscribeModelExtractor subscribeModelExtractor;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Receipt subscribe(WriteContext context, Form form) throws Conflict {

        AnSubscribeModelExtractor.ModelSubscription modelSubscription = subscribeModelExtractor.extract(
                form, context.getOwnerId(), context.getOperatorId(), TREE_MASTER);

        ModelSubscriptionDai.ModelSubscription subscription = DataTools.copy(modelSubscription, ModelSubscriptionDai.ModelSubscription.class);

        try {
            modelSubscriptionDai.save(subscription);
            return DataTools.copy(modelSubscription, Receipt.class);
        } catch (ModelSubscriptionDai.DuplicatedSubscription duplicatedSubscription) {
            throw new Conflict(subscription.getId());
        }
    }


}
