package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */
public interface SearchModelsApi {


    List<Provider> search(ReadContext context, QueryProvidersForm form);

    List<Models> search(ReadContext context, QueryModelsForm form);

    @Data
    class Provider {
        String providerId;
    }

    @Data
    class Models {
        String providerId;

        String group;

        String name;

        List<Snapshot> snapshots;
    }

    @Data
    class QueryProvidersForm {
        private String id;
    }

    @Data
    class Snapshot {
        int subscriptions;
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
