package com.sinewang.kiimate.model.cases.spi;


import one.kii.kiimate.model.cases.spi.DeclareExtensionSpi;
import one.kii.summer.io.exception.*;
import one.kii.summer.io.sender.ErestPost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Created by WangYanJiong on 4/7/17.
 */
@ConfigurationProperties(prefix = "metamate")
@Component
public class DefaultDeclareExtensionSpi implements DeclareExtensionSpi {

    private static Logger logger = LoggerFactory.getLogger(DefaultDeclareExtensionSpi.class);

    private static String URI = "/{ownerId}/extensions";

    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public Receipt commit(Form form) throws Panic, Conflict, BadRequest, NotFound, Forbidden {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(form.getOwnerId());

        return erest.execute(url, form, Receipt.class, form.getOwnerId());
    }
}
