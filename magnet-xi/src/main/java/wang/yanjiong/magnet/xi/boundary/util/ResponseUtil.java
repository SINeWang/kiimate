package wang.yanjiong.magnet.xi.boundary.util;

import org.springframework.beans.BeanUtils;
import wang.yanjiong.magnet.xi.boundary.Context;
import wang.yanjiong.magnet.xi.boundary.Request;
import wang.yanjiong.magnet.xi.boundary.Response;
import wang.yanjiong.magnet.xi.boundary.Summary;

import java.util.Date;
import java.util.UUID;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public class ResponseUtil {

    public static <U, V extends Response> V build(Request form, V response, U data) {
        Summary summary = new Summary();
        summary.setStatus(Summary.Status.ACCEPTED);
        summary.setTime(new Date());

        response.setSummary(summary);

        Context context = new Context();
        BeanUtils.copyProperties(context, form.getContext());
        context.setResponseId(UUID.randomUUID().toString());
        response.setContext(context);

        BeanUtils.copyProperties(data, response);
        return response;
    }

    public static <U, V extends Response> V build(Request form, Class<V> responseClass, U data) {
        V response = null;
        try {
            response = responseClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return build(form, response, data);
    }
}
