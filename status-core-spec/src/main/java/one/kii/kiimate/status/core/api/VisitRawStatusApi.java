package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitRawStatusApi {

    String TREE_MASTER = "master";


    Map<String, Object> visit(ReadContext context, GroupNameTreeForm form) throws NotFound;

    @Data
    class GroupNameTreeForm {
        String group;
        String name;
        String tree = TREE_MASTER;
    }

}
