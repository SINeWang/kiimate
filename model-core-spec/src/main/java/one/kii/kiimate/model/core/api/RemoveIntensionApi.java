package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface RemoveIntensionApi {


    Receipt removeIntension(WriteContext context, Form form) throws Conflict;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String intId;

        private String ownerId;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

    }

}
