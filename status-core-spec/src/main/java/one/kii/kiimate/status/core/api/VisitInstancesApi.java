package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitInstancesApi {


    Receipt visit(ReadContext context, Form form) throws NotFound;

    @Data
    class Form {
        String ownerId;
        String subId;
    }

    @Data
    class Receipt {
        String ownerId;
        Map<String, Object> instances;
    }
}
