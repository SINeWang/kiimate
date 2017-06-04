package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.CommitApi;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;


/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface RefreshStatusApi extends CommitApi<RefreshStatusApi.Receipt, WriteContext, RefreshStatusApi.SubIdForm> {

    Receipt commit(WriteContext context, SubIdForm form) throws BadRequest, Conflict, NotFound, Panic;

    @Data
    class SubIdForm {
        String subId;
        MultiValueMap<String, String> map;
    }


    @Data
    class Receipt {
        String subId;
        String ownerId;
        List<Intension> intensions;
        Map<String, Object> map;
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
