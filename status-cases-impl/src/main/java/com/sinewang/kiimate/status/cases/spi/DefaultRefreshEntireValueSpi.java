package com.sinewang.kiimate.status.cases.spi;

import one.kii.kiimate.status.cases.spi.RefreshEntireValueSpi;
import one.kii.summer.io.exception.*;
import one.kii.summer.io.sender.ErestPut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 7/4/17.
 */
@Component
public class DefaultRefreshEntireValueSpi implements RefreshEntireValueSpi {

    private static String URI_SUB_ID = "/{ownerId}/value/{sub-id}";

    @Value("${kiimate.url}")
    private String url;

    @Value("${kiimate.operator-id}")
    private String operatorId;

    @Override
    public void commit(IdForm form) throws Panic, Conflict, BadRequest, NotFound, Forbidden {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        extractAsMap(form.getObject(), map);
        saveInstance(form, map);
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


    private void saveInstance(IdForm form, MultiValueMap<String, String> map) throws Panic, Conflict, BadRequest, NotFound, Forbidden {
        String urlTemplate = url + URI_SUB_ID;
        ErestPut put = new ErestPut(operatorId);
        put.execute(urlTemplate, map, null, form.getOwnerId(), form.getId());
    }
}
