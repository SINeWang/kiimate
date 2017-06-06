package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.fui.AnStatusExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultSubscribeModelsApi implements SubscribeModelsApi {

    @Autowired
    private AnStatusExtractor subscribeModelExtractor;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws Conflict, Panic {


        ModelSubscriptionDai.Status status = subscribeModelExtractor.extract(form, context);

        modelSubscriptionDai.remember(status);

        Receipt receipt = ValueMapping.from(Receipt.class, status);

        return NotBadResponse.of(receipt);
    }


}
