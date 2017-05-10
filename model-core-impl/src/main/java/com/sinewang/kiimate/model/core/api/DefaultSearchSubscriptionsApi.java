package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SearchSubscriptionsApi;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
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
        return Collections.emptyList();
    }
}
