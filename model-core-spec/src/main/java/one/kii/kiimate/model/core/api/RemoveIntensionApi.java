package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface RemoveIntensionApi {


    Receipt commit(WriteContext context, Form form) throws Conflict, NotFound;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        String ownerId;

        Long extId;

        Long id;
    }

    @Data
    class Receipt {

        List<Intension> intensions;

        Map<String, Object> schema;
    }


    @Data
    class Intension {

        private String id;

        private String field;

        private Boolean single;

        private String structure;

        private String refPubSet;

        private String visibility;

        private Boolean required;
    }

}
