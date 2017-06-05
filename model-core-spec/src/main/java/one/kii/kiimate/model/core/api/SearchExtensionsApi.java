package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.SearchApi;
import one.kii.summer.io.context.ReadContext;

import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */
public interface SearchExtensionsApi extends SearchApi<List<SearchExtensionsApi.Extension>, ReadContext, SearchExtensionsApi.QueryForm> {


    List<Extension> search(ReadContext context, QueryForm form);


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
