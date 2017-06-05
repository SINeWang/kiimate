package com.sinewang.kiimate.status.core.api;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.status.core.api.SubscribeAssetApi;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by WangYanJiong on 22/05/2017.
 */
@Component
public class DefaultSubscribeAssetApi implements SubscribeAssetApi {

    private static final Eid64Generator idgen = new Eid64Generator(7);


    @Autowired
    private AssetDai assetDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {

        AssetDai.Subscription subscription = ValueMapping.from(AssetDai.Subscription.class, form, context);

        subscription.setSubscriberId(context.getOwnerId());

        subscription.setBeginTime(new Date());

        subscription.setId(idgen.born());

        assetDai.remember(subscription);

        return ValueMapping.from(Receipt.class, subscription);

    }

}