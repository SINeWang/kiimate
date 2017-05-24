package com.sinewang.kiimate.model.cases.spi;

import one.kii.kiimate.model.cases.spi.SubscribeModelsSpi;
import one.kii.summer.io.exception.*;
import one.kii.summer.io.sender.ErestPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 09/04/2017.
 */
@Component

public class DefaultSubscribeModelsSpi implements SubscribeModelsSpi {

    private static Logger logger = LoggerFactory.getLogger(DefaultSubscribeModelsSpi.class);

    private static String URI = "/{subscriberId}/subscriptions/models";

    @Value("${kiimate.url}")
    private String url;

    @Value("${kiimate.operator-id}")
    private String operatorId;


    @Override
    public Receipt commit(Form form) throws Panic, BadRequest, Forbidden, Conflict, NotFound {
        String urlTemplate = url + URI;

        ErestPost erest = new ErestPost(operatorId);
        logger.info("url:{}, form:{}", url, form);
        try {
            return erest.execute(urlTemplate, form, Receipt.class, form.getSubscriberId());
        } catch (Panic | BadRequest | Forbidden | Conflict | NotFound oops) {
            logger.error(oops.getClass().getName() + ": url={}, form={}", urlTemplate, form);
            throw oops;
        }
    }
}