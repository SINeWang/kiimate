package com.sinewang.statemate.core.spi;

import one.kii.summer.bound.Receipt;
import one.kii.summer.erest.GetWithOAuth2AccessToken;
import wang.yanjiong.statemate.core.spi.GetStateSpi;

/**
 * Created by WangYanJiong on 02/04/2017.
 */
public class DefaultGetStateSpi implements GetStateSpi {

    @Override
    public <T> Receipt<T> getLatestState(String ownerId, String group, String name, String tag, Class<T> stateClass) {

        GetWithOAuth2AccessToken getWithOAuth2AccessToken = new GetWithOAuth2AccessToken("abc");

        Object object = getWithOAuth2AccessToken.getAny("http://localhost:8080", Object.class);

        return null;
    }
}
