package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 31/05/2017.
 */
public interface VisitModelApi extends VisitApi<VisitModelApi.Model, ReadContext, VisitModelApi.VisitModelForm> {


    Model visit(ReadContext context, VisitModelForm form) throws NotFound;

    @Data
    class Model {

        Integer subscriptions;

        String providerId;
        String pubSet;
        String rootExtId;

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

        private Long refPubSet;

        private String visibility;
    }

    @Data
    class VisitModelForm {
        private Long pubSet;
    }
}
