package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.PublishAssetApi;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
@Component
public class DefaultPublishAssetApi implements PublishAssetApi {

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {
        return null;
    }
}
