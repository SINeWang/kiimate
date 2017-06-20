package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.CommitApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;

import java.util.Date;

/**
 * Created by WangYanJiong on 22/05/2017.
 */
public interface SubscribeGlimpseApi extends CommitApi<SubscribeGlimpseApi.Receipt, WriteContext, SubscribeGlimpseApi.Form> {


    Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound;

    @Data
    class Form {

        private String set;

    }

    @Data
    class Receipt {

        private String id;

        private String subscriberId;

        private String set;

        private Date beginTime;

        @MayHave
        private Date endTime;

    }

}
