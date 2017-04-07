package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.SaveStateSpi;
import one.kii.summer.erest.ErestPostFormUrlEncoded;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 4/7/17.
 */
@ConfigurationProperties(prefix = "metamate")
@Component
public class DefaultSaveStateSpi implements SaveStateSpi {

    private static String URI = "/{ownerId}/instance/{group}/{tree}";

    private static String TREE = "master";

    private String ownerId;

    private String visitorId;

    private String baseUrl;

    private String operatorId;

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }


    @Override
    public <T> void save(String group, T object) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        extractAsMap(object, map);
        saveInstance(group, map);
    }

    private <T> void extractAsMap(T object, MultiValueMap<String, String> map) {
        Class klass = object.getClass();
        for (Field field : klass.getDeclaredFields()) {
            String fieldName = field.getName();
            if (fieldName.startsWith("this$")) {
                continue;
            }
            Class type = field.getType();
            field.setAccessible(true);
            Object value = null;
            try {
                value = field.get(object);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            boolean single = type.getComponentType() == null;
            if (!single) {
                type = type.getComponentType();
            }
            if (String.class.getName().equals(type.getName()) || type.isPrimitive()) {
                List<String> list = new ArrayList<>();
                if (single) {
                    list.add(String.valueOf(value));
                } else {
                    Object[] values = (Object[]) value;
                    for (Object v : values) {
                        list.add(String.valueOf(v));
                    }
                }
                map.put(fieldName, list);
            } else {
                extractAsMap(value, map);
            }
        }
    }

    private void saveInstance(String group, MultiValueMap<String, String> map) {
        String url = baseUrl + URI;
        ErestPostFormUrlEncoded erestPost = new ErestPostFormUrlEncoded();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-MM-OperatorId", operatorId);

        erestPost.doPost(url, httpHeaders, map, Receipt.class, ownerId, group, TREE);
    }
}
