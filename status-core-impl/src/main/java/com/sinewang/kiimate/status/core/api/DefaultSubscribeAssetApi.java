package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SubscribeAssetApi;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;

/**
 * Created by WangYanJiong on 22/05/2017.
 */
public class DefaultSubscribeAssetApi implements SubscribeAssetApi {

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {
        return null;
    }

}
