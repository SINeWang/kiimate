package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.SearchApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.Panic;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */
public interface SearchModelsApi extends SearchApi<List<SearchModelsApi.Models>, ReadContext, SearchModelsApi.QueryModelsForm> {


    List<Models> search(ReadContext context, QueryModelsForm form) throws Panic;

    @Data
    class Models {
        String providerId;

        String group;

        String name;

        String id;

        List<Snapshot> snapshots;
    }

    @Data
    class Snapshot {
        Integer subscriptions;
        String pubSet;
        String stability;
        String version;
        Date beginTime;
    }

    @Data
    class QueryModelsForm {
        private String group;
        private String stability;
        private String version;
    }
}
