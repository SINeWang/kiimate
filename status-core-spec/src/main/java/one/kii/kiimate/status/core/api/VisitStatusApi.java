package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.VisitDownWithXyz;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitStatusApi extends VisitApi<VisitStatusApi.Status, ReadContext, VisitDownWithXyz> {


    Status visit(ReadContext context, VisitDownWithXyz form) throws NotFound, BadRequest, Panic;

    @Data
    class Status {
        String id;
        String providerId;
        String group;
        String name;
        String stability;
        String version;
        Map<String, Object> map;
        List<Intension> intensions;
        Date beginTime;
        Date endTime;
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
