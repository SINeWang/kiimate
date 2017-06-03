package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SubscribeAssetApi;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.summer.beans.utils.HashTools;
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

    @Autowired
    private AssetDai assetDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {


        AssetDai.Record record = ValueMapping.from(AssetDai.Record.class, form, context);

        record.setSubscriberId(context.getOwnerId());

        record.setBeginTime(new Date());
        String id = HashTools.hashHex(record);
        record.setId(id);

        assetDai.save(record);

        return ValueMapping.from(Receipt.class, record);

    }

}