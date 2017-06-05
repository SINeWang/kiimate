package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.SearchApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;

import java.util.List;

/**
 * Created by WangYanJiong on 10/05/2017.
 */
public interface SearchSubscriptionsApi extends SearchApi<List<SearchSubscriptionsApi.Subscriptions>, ReadContext, SearchSubscriptionsApi.QueryForm> {


    List<Subscriptions> search(ReadContext context, QueryForm form) throws BadRequest;


    @Data
    class Subscriptions {

        String id;

        String subSet;

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
