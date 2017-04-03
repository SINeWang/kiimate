package com.sinewang.statemate.core.spi;

import one.kii.summer.bound.Receipt;
import one.kii.summer.erest.GetWithOAuth2AccessToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import wang.yanjiong.statemate.core.spi.GetStateSpi;

/**
 * Created by WangYanJiong on 02/04/2017.
 */

@ConfigurationProperties(prefix = "statemate.metamate")
@Component
public class DefaultGetStateSpi implements GetStateSpi {

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public <T> Receipt<T> getLatestState(String ownerId, String group, String name, String tag, Class<T> stateClass) {

        GetWithOAuth2AccessToken getWithOAuth2AccessToken = new GetWithOAuth2AccessToken("abc");

        Object object = getWithOAuth2AccessToken.getAny(url, Object.class);

        return null;
    }
}
