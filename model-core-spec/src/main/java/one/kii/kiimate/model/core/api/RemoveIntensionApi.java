package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;

import java.util.List;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface RemoveIntensionApi {


    List<Intension> removeIntension(WriteContext context, Form form) throws Conflict;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String extId;

        private String intId;

        private String ownerId;

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