package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.SearchApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;

import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */
public interface SearchExtensionsApi extends SearchApi<SearchExtensionsApi.Extension, ReadContext, SearchExtensionsApi.QueryForm> {


    List<Extension> search(ReadContext context, QueryForm form) throws BadRequest;


    @Data
    class Extension {
        String ownerId;
        String group;
        String name;
        String tree;
        String id;
        String visibility;
    }

    @Data
    class QueryForm {
        private String ownerId;
        private String group;
    }

}
