package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.Map;

/**
 * Created by WangYanJiong on 23/05/2017.
 */
public interface VisitRawAssetApi extends VisitApi<Map<String, Object>, ReadContext, VisitRawAssetApi.GroupNameForm> {


    String STABILITY_LATEST = "latest";

    String VERSION_HEAD = "HEAD";

    Map<String, Object> visit(ReadContext context, GroupNameForm form) throws BadRequest, NotFound, Panic;

    @Data
    class GroupNameForm {
        String group;
        String name;
        String stability = STABILITY_LATEST;
        String version = VERSION_HEAD;
    }

}
