package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.CommitApi;
import one.kii.summer.beans.annotations.Unique;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.List;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface PublishModelApi extends CommitApi<PublishModelApi.Receipt, WriteContext, PublishModelApi.Form> {


    Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic;

    @Data
    class Form {

        @Unique
        private String providerId;

        @Unique
        private Long extId;

        @Unique
        private String stability;

        @Unique
        private String version;

    }

    @Data
    class Receipt {

        List<Intension> intensions;

        private String pubSet;

        private String providerId;

        private String ownerId;

        private String version;

    }

    @Data
    class Intension {

        String field;

        Boolean single;
    }


}
