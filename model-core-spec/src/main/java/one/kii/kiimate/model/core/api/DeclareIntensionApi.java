package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.asdf.api.CommitApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface DeclareIntensionApi extends CommitApi<DeclareIntensionApi.Receipt, WriteContext, DeclareIntensionApi.Form> {


    Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String extId;

        private String field;

        private Boolean single;

        private String structure;

        @MayHave
        private String refSet;

        private String visibility;

        private Boolean required;
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

        private String refSet;

        private String visibility;

        private Boolean required;
    }

}
