package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.CommitApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.Map;


/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface RefreshPartialValueApi extends CommitApi<RefreshPartialValueApi.Receipt, WriteContext, RefreshPartialValueApi.SubIdForm> {

    Receipt commit(WriteContext context, SubIdForm form) throws BadRequest, Conflict, NotFound, Panic;

    @Data
    class SubIdForm {

        String id;

        String field;

        Value[] values;
    }

    @Data
    class Value {

        Boolean reference;

        String value;

        @MayHave
        String glimpseId;
    }


    @Data
    class Receipt {

        String id;

        String ownerId;

        Map<String, Object> map;
    }

}
