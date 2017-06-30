package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 31/05/2017.
 */
public interface VisitModelApi extends VisitApi<VisitModelApi.Model, ReadContext, VisitModelApi.VisitModelForm> {


    Model visit(ReadContext context, VisitModelForm form) throws BadRequest, NotFound, Panic;

    @Data
    class Model {

        Integer subscriptions;

        String providerId;
        String set;

        String group;
        String name;

        String stability;
        String version;
        Date beginTime;

        List<Intension> intensions;
    }

    @Data
    class Intension {

        private String id;

        private String field;

        private Boolean single;

        private String structure;

        private String refSet;

        private String visibility;
    }

    @Data
    class VisitModelForm {
        private String set;
    }
}
