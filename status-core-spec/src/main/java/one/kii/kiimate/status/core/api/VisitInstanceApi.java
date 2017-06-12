package one.kii.kiimate.status.core.api;

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
 * Created by WangYanJiong on 03/06/2017.
 */
public interface VisitInstanceApi extends VisitApi<VisitInstanceApi.Instance, ReadContext, ZoomInById> {


    Instance visit(ReadContext context, ZoomInById form) throws BadRequest, NotFound, Panic;

    @Data
    class Instance {
        List<Intension> intensions;
        Map<String, Object> map;
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
