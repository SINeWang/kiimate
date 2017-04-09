package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.ReadStateSpi;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 09/04/2017.
 */

@Component
public class DefaultReadStateSpi implements ReadStateSpi {


    private static String URI = "/{ownerId}/extension/{group}/{name}/{tree}";
    private static String TREE = "master";
    private String ownerId;
    private String visitorId;
    private String baseUrl;



    @Override
    public <T> State<T> getLatestState(String ownerId, String group, String name, Class<T> state) {
        return null;
    }


}
