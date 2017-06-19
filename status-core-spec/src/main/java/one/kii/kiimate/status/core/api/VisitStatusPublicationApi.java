package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomOutBySet;

import java.util.Date;

/**
 * Created by WangYanJiong on 19/6/17.
 */

public interface VisitStatusPublicationApi extends VisitApi<VisitStatusPublicationApi.Publication, ReadContext, ZoomOutBySet> {


    Publication visit(ReadContext context, ZoomOutBySet form) throws NotFound, BadRequest, Panic;


    @Data
    class Publication {

        Outside model;
        Outside status;
    }

    @Data
    class Outside {
        private String id;
        private String set;
        private String providerId;
        private String group;
        private String name;
        private String stability;
        private String version;
        private Date beginTime;
        @MayHave
        private Date endTime;
    }


}
