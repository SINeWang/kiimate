package com.sinewang.statemate.core.spi;

import one.kii.statemate.core.spi.SaveStateSpi;
import one.kii.summer.erest.ErestPostForm;
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

    private static String URI = "/{ownerId}/instance/{group}/{name}/{tree}";

    private static String TREE = "master";

    private String baseUrl;

    private String operatorId;

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public <T> void save(Form<T> form) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        extractAsMap(form.getObject(), map);
        saveInstance(form.getOwnerId(), form.getGroup(), form.getName(), map);
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
            if (value == null) {
                continue;
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
                    if (value instanceof int[]) {
                        for (int v : (int[]) value) {
                            list.add(String.valueOf(v));
                        }
                    } else {
                        for (Object v : (Object[]) value) {
                            list.add(String.valueOf(v));
                        }
                    }
                }
                map.put(fieldName, list);
            } else {
                extractAsMap(value, map);
            }
        }
    }

    private void saveInstance(String ownerId, String group, String name, MultiValueMap<String, String> map) {
        String url = baseUrl + URI;
        ErestPostForm erestPost = new ErestPostForm();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("X-MM-OperatorId", operatorId);


        erestPost.doPost(url, httpHeaders, map, Receipt.class, ownerId, group, name, TREE);
    }
}
