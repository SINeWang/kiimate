package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */
public interface VisitAssetsApi {


    String STABILITY_LATEST = "latest";

    String VERSION_HEAD = "HEAD";

    Assets visit(ReadContext context, PubSetForm form) throws NotFound;

    Assets visit(ReadContext context, GroupNameForm form) throws NotFound;

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
    class PubSetForm {
        String pubSet;
        String version;
    }

    @Data
    class GroupNameForm {
        String group;
        String name;
        String stability = STABILITY_LATEST;
        String version = VERSION_HEAD;
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
