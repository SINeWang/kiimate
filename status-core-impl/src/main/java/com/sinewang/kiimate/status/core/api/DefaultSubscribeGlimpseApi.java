package com.sinewang.kiimate.status.core.api;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.status.core.api.SubscribeGlimpseApi;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.validator.NotBadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by WangYanJiong on 22/05/2017.
 */
@Component
public class DefaultSubscribeGlimpseApi implements SubscribeGlimpseApi {

    private static final Eid64Generator idgen = new Eid64Generator(7);


    @Autowired
    private GlimpsesDai glimpsesDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {
        NotBadRequest.from(form);

        GlimpsesDai.Glimpse glimpse = ValueMapping.from(GlimpsesDai.Glimpse.class, form, context);

        glimpse.setSubscriberId(context.getOwnerId());

        glimpse.setBeginTime(new Date());

        glimpse.setId(idgen.born());

        glimpsesDai.remember(glimpse);

        return ValueMapping.from(Receipt.class, glimpse);

    }

}