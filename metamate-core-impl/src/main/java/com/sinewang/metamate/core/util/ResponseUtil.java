package com.sinewang.metamate.core.util;

import org.springframework.beans.BeanUtils;
import wang.yanjiong.metamate.core.model.Context;
import wang.yanjiong.metamate.core.model.Request;
import wang.yanjiong.metamate.core.model.Response;

import java.util.UUID;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public class ResponseUtil {

    public static <U, V extends Response<U>> V buildReceipt(Request form, V response, U data) {
        Context context = new Context();
        BeanUtils.copyProperties(context, form.getContext());
        context.setResponseId(UUID.randomUUID().toString());
        response.setContext(context);
        response.setData(data);
        return response;
    }

    public static <U, V extends Response<U>> V buildReceipt(Request form, Class<V> responseClass, U data) {
        V response = null;
        try {
            response = responseClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Context context = new Context();
        BeanUtils.copyProperties(context, form.getContext());
        context.setResponseId(UUID.randomUUID().toString());
        response.setContext(context);
        response.setData(data);
        return response;
    }
}
