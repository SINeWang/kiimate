package com.sinewang.kiimate.model.cases.spi;

import one.kii.kiimate.model.cases.spi.DeclareIntensionSpi;
import one.kii.summer.io.exception.*;
import one.kii.summer.io.sender.ErestPost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * Created by WangYanJiong on 4/7/17.
 */
@ConfigurationProperties(prefix = "kiimate")
@Component
public class DefaultDeclareIntensionSpi implements DeclareIntensionSpi {

    private static String TREE = "master";
    private static String URI = "/{ownerId}/intension";

    private String baseUrl;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String commit(PrimitiveIntensionForm form) throws Panic, Conflict, BadRequest, NotFound, Forbidden {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(form.getOwnerId());

        IntensionReceipt receipt = erest.execute(url, form, IntensionReceipt.class, form.getOwnerId());
        return receipt.getId();
    }

    @Override
    public String commit(ImportIntensionForm form) throws Panic, Conflict, BadRequest, NotFound, Forbidden {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(form.getOwnerId());

        IntensionReceipt receipt = erest.execute(url, form, IntensionReceipt.class, form.getOwnerId());
        return receipt.getExtId();
    }


}
