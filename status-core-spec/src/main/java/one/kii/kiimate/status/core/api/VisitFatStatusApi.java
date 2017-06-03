package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.xi.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 03/06/2017.
 */
public interface VisitFatStatusApi extends VisitApi<VisitFatStatusApi.Status, ReadContext, VisitFatStatusApi.StatusIdForm> {


    Status visit(ReadContext context, StatusIdForm form) throws NotFound;

    @Data
    class Status {
        List<Intension> intensions;
        Map<String, Object> map;
    }

    @Data
    class StatusIdForm {
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
