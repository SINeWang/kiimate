package one.kii.kiimate.status.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.asdf.xi.CommitApi;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.Date;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface PublishStatusApi extends CommitApi<PublishStatusApi.Receipt, WriteContext, PublishStatusApi.Form> {

    Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String stability;

        private String providerId;

        private Long subId;

        private String version;

        private String visibility;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        private String pubSet;

        private String group;

        private String name;

        private String providerId;

        private String version;

        private String visibility;

        private Date beginTime;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {

        private String field;

        private String[] value;
    }
}
