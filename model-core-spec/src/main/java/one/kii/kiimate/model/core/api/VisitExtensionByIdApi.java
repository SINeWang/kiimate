package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomInById;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 27/6/17.
 */

public interface VisitExtensionByIdApi extends VisitApi<VisitExtensionByIdApi.Receipt, ReadContext, ZoomInById> {


    Receipt visit(ReadContext context, ZoomInById form) throws BadRequest, NotFound, Panic;


    @Data
    class Receipt {
        String id;
        String ownerId;
        String group;
        String name;
        String tree;
        String visibility;
        List<Intension> intensions;
        Map<String, Object> schema;
    }

    @Data
    class Intension {

        private String id;

        private String field;

        private Boolean single;

        private String structure;

        private String refSet;

        private String visibility;

        private Boolean required;
    }

}
