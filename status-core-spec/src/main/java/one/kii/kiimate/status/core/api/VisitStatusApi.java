package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitStatusApi {

    String TREE_MASTER = "master";


    Receipt visit(ReadContext context, GroupNameTreeForm form) throws NotFound;

    @Data
    class Receipt {
        String subId;
        String ownerId;
        List<Intension> intensions;
        Map<String, Object> map;
    }

    @Data
    class GroupNameTreeForm {
        String group;
        String name;
        String tree = TREE_MASTER;
    }

    @Data
    class Intension {

        private Long id;

        private String field;

        private Boolean single;

        private String structure;

        private Long refPubSet;

        private String visibility;

        private Long required;
    }
}
