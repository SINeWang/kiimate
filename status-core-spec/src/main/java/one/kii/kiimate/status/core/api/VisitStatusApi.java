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


    Receipt visit(ReadContext context, Form form) throws NotFound;

    @Data
    class Form {
        String ownerId;
        String subId;
    }

    @Data
    class Receipt {
        String subId;
        String ownerId;
        List<Intension> intensions;
        Map<String, Object> map;
    }


    @Data
    class Intension {

        private String id;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

        private boolean required;
    }
}
