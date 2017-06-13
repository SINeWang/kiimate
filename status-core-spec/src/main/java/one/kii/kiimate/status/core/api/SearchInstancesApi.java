package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.SearchApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;

import java.util.List;

/**
 * Created by WangYanJiong on 10/05/2017.
 */
public interface SearchInstancesApi extends SearchApi<SearchInstancesApi.Instance, ReadContext, SearchInstancesApi.QueryForm> {


    List<Instance> search(ReadContext context, QueryForm form) throws BadRequest, Panic;


    @Data
    class Instance {

        String id;

        String set;

        String subscriberId;

        String operatorId;

        String group;

        String name;

        String tree;
    }

    @Data
    class QueryForm {
        private String group;
    }

}
