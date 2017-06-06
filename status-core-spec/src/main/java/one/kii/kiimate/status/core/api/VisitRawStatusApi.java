package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitRawStatusApi extends VisitApi<Map<String, Object>, ReadContext, VisitRawStatusApi.GroupNameTreeForm> {

    String TREE_MASTER = "master";


    Map<String, Object> visit(ReadContext context, GroupNameTreeForm form) throws NotFound, BadRequest, Panic;

    @Data
    class GroupNameTreeForm {
        String group;
        String name;
        String tree = TREE_MASTER;
    }

}
