package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.VisitUpWithId;
import one.kii.summer.xyz.VisitUpWithXyz;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */

public interface VisitFatAssetApi extends VisitApi {


    Asset visit(ReadContext context, VisitUpWithXyz form) throws BadRequest, NotFound, Panic;

    Asset visit(ReadContext context, VisitUpWithId form) throws BadRequest, NotFound, Panic;

    @Data
    class Asset {
        String ownerId;
        String group;
        String name;
        String stability;
        String version;
        List<Intension> intensions;
        Map<String, Object> map;
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
