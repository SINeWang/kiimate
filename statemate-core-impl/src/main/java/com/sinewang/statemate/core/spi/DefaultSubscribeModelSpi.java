package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.SubscribeModelSpi;
import one.kii.summer.erest.ErestPost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 09/04/2017.
 */
@Component
@ConfigurationProperties(prefix = "metamate")
public class DefaultSubscribeModelSpi implements SubscribeModelSpi {


    private static String URI = "/{subscriberId}/subscribe";

    private String baseUrl;

    private String subscriberId;

    private String operatorId;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    @Override
    public Receipt subscribe(Form form) {
        String url = baseUrl + URI;

        ErestPost erest = new ErestPost(operatorId);

        return erest.execute(url, form, Receipt.class, form.getSubscriberId());
    }
}
