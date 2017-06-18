package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.CommitApi;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.util.MultiValueMap;

import java.util.Map;


/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface RefreshInstanceApi extends CommitApi<RefreshInstanceApi.Receipt, WriteContext, RefreshInstanceApi.SubIdForm> {

    Receipt commit(WriteContext context, SubIdForm form) throws BadRequest, Conflict, NotFound, Panic;

    @Data
    class SubIdForm {
        String id;
        MultiValueMap<String, String> map;
    }


    @Data
    class Receipt {
        String id;
        String ownerId;
        Map<String, Object> map;
    }

}
