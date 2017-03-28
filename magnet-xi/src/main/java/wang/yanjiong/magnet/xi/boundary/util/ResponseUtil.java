package wang.yanjiong.magnet.xi.boundary.util;

import org.springframework.beans.BeanUtils;
import wang.yanjiong.magnet.xi.boundary.Context;
import wang.yanjiong.magnet.xi.boundary.Request;
import wang.yanjiong.magnet.xi.boundary.Response;
import wang.yanjiong.magnet.xi.boundary.Summary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public class ResponseUtil {

    private static <U, V extends Response> V build(Request form, V response, U data) {
        Context context = new Context();
        BeanUtils.copyProperties(context, form.getContext());
        context.setResponseId(UUID.randomUUID().toString());
        response.setContext(context);

        BeanUtils.copyProperties(data, response);
        return response;
    }

    public static <U, V extends Response> V accepted(Request form, Class<V> responseClass, U data) {
        V response = null;
        try {
            response = responseClass.newInstance();
            Summary summary = new Summary();
            summary.setStatus(Summary.Status.ACCEPTED);
            summary.setTime(new Date());
            response.setSummary(summary);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return build(form, response, data);
    }

    public static <U, V extends Response> V rejected(Request form, Class<V> responseClass, U data, String... reasons) {
        V response = null;
        try {
            response = responseClass.newInstance();
            Summary summary = new Summary();
            summary.setStatus(Summary.Status.REJECTED);
            summary.setTime(new Date());
            summary.setReasons(Arrays.asList(reasons));
            response.setSummary(summary);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return build(form, response, data);
    }
}
