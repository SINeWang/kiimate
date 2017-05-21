package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */
public interface VisitAssetsApi {


    List<Assets> visit(ReadContext context, SearchAssetsApi.QueryForm form);

    @Data
    class Assets {
        String pubSet;
        String ownerId;
        String visibility;
        String group;
        String name;
        String stability;
        String version;
        List<Intension> intensions;

        Map<String, Object> map;
    }

    @Data
    class QueryForm {
        String pubSet;
        String version;
    }

    @Data
    class Intension {

        private String id;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

        private boolean required;
    }

}
