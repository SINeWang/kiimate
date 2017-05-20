package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;

import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
public interface SearchAssetsApi {


    List<Assets> search(ReadContext context, QueryForm form);

    @Data
    class Assets {
        String pubSet;
        String ownerId;
        String visibility;
        String group;
        String name;
        String stability;
        String version;
    }

    @Data
    class QueryForm {
        String query;
    }

}
