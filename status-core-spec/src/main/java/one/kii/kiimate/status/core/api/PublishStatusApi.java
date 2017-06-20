package one.kii.kiimate.status.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.asdf.api.CommitApi;
import one.kii.summer.io.annotations.MayHave;
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

        private Long id;

        private String providerId;

        private String stability;

        private String version;

        private String visibility;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        private String set;

        private String providerId;

        private String version;

        private String visibility;

        private Date beginTime;

        @MayHave
        private Date endTime;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {

        private String field;

        private String[] value;
    }
}
