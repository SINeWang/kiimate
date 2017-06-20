package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomOutByName;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitFatStatusApi extends VisitApi<VisitFatStatusApi.Status, ReadContext, ZoomOutByName> {


    Status visit(ReadContext context, ZoomOutByName form) throws NotFound, BadRequest, Panic;

    @Data
    class Status {
        private String id;

        private String set;

        private String providerId;

        private String group;

        private String name;

        private String stability;

        private String version;

        private Map<String, Object> map;

        private List<Intension> intensions;

        private Date beginTime;
        @MayHave
        private Date endTime;
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
