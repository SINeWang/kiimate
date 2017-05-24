package com.sinewang.kiimate.status.cases.spi;

import one.kii.kiimate.status.cases.spi.RefreshStatusSpi;
import one.kii.summer.io.exception.*;
import one.kii.summer.io.sender.ErestPost;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 4/7/17.
 */
@ConfigurationProperties(prefix = "kiimate")
@Component
public class DefaultRefreshStatusSpi implements RefreshStatusSpi {

    private static String URI = "/{ownerId}/instance/{group}/{name}/{tree}";

    private static String TREE = "master";

    private String url;

    private String operatorId;

    @Override
    public <T> void commit(RefreshStatusSpi.Form<T> form) throws Panic {
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

    private void saveInstance(String ownerId, String group, String name, MultiValueMap<String, String> map) throws Panic {
        String baseUrl = url + URI;
        ErestPost erestPost = new ErestPost(operatorId);

        try {
            erestPost.execute(baseUrl, map, Receipt.class, ownerId, group, name, TREE);
        } catch (Panic panic) {
            panic.printStackTrace();
        } catch (BadRequest badRequest) {
            badRequest.printStackTrace();
        } catch (Conflict conflict) {
            conflict.printStackTrace();
        } catch (NotFound notFound) {
            notFound.printStackTrace();
        } catch (Forbidden forbidden) {
            forbidden.printStackTrace();
        }
        throw new Panic();
    }
}
