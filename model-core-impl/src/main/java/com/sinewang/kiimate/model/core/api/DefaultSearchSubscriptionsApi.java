package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SearchSubscriptionsApi;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 10/05/2017.
 */

@Component
public class DefaultSearchSubscriptionsApi implements SearchSubscriptionsApi {

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public List<Subscriptions> search(ReadContext context, QueryForm form) {
        ModelSubscriptionDai.ClueGroup clue = ValueMapping.from(ModelSubscriptionDai.ClueGroup.class, context, form);
        List<ModelSubscriptionDai.Status> list = modelSubscriptionDai.querySubscriptions(clue);
        return ValueMapping.from(Subscriptions.class, list);
    }
}
