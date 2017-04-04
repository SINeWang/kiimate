package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.ReadStateSpi;
import one.kii.summer.erest.GetWithOAuth2AccessToken;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 02/04/2017.
 */

@ConfigurationProperties(prefix = "statemate.metamate")
@Component
public class DefaultReadStateSpi implements ReadStateSpi {

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public <T> State<T> getLatestState(String ownerId, String group, String name, Class<T> state) {
        GetWithOAuth2AccessToken getWithOAuth2AccessToken = new GetWithOAuth2AccessToken("abc");

        Object object = getWithOAuth2AccessToken.forAny(url, Object.class);
        return null;
    }

    @Override
    public <T> State<T> getState(String ownerId, String group, String name, String tag, Class<T> state) {
        GetWithOAuth2AccessToken getWithOAuth2AccessToken = new GetWithOAuth2AccessToken("abc");

        Object object = getWithOAuth2AccessToken.forAny(url, Object.class);
        return null;
    }
}
