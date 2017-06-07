package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.SearchApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
public interface SearchStatusesApi extends SearchApi<SearchStatusesApi.Statuses, ReadContext, SearchStatusesApi.QueryForm> {


    List<Statuses> search(ReadContext context, QueryForm form) throws BadRequest, Panic;

    @Data
    class Statuses {

        String id;

        Long pubSet;

        String providerId;

        String group;

        String name;

        String stability;

        String version;

        Date beginTime;

        @MayHave
        Date endTime;
    }

    @Data
    class QueryForm {
        String query;
    }

}
