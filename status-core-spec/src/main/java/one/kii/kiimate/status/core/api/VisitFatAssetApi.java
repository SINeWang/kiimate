package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */

public interface VisitFatAssetApi extends VisitApi {


    String STABILITY_LATEST = "latest";

    String VERSION_HEAD = "HEAD";

    Asset visit(ReadContext context, GroupNameForm form) throws BadRequest, NotFound, Panic;

    Asset visit(ReadContext context, OwnerIdForm form) throws BadRequest, NotFound, Panic;

    @Data
    class Asset {
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
    class GroupNameForm {
        String group;
        String name;
        String stability = STABILITY_LATEST;
        String version = VERSION_HEAD;
    }

    @Data
    class OwnerIdForm {
        String ownerId;
        String id;
    }

    @Data
    class Intension {

        private String id;

        private String field;

        private Boolean single;

        private String structure;

        private String refPubSet;

        private String visibility;

        private Boolean required;
    }

}
