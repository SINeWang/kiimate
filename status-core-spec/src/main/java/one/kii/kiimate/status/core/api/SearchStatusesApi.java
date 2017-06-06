package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.SearchApi;
import one.kii.summer.io.context.ReadContext;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
public interface SearchStatusesApi extends SearchApi<SearchStatusesApi.Statuses, ReadContext, SearchStatusesApi.QueryForm> {


    List<Statuses> search(ReadContext context, QueryForm form);

    @Data
    class Statuses {

        String id;

        Long pubSet;

        String providerId;

        String stability;

        String version;

        Date beginTime;

        Date endTime;
    }

    @Data
    class QueryForm {
        String query;
    }

}
