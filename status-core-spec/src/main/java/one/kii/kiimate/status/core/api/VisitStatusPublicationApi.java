package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.beans.annotations.Unique;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomOutBySet;

import java.util.Date;
import java.util.Map;

/**
 * Created by WangYanJiong on 19/6/17.
 */

public interface VisitStatusPublicationApi extends VisitApi<VisitStatusPublicationApi.Publication, ReadContext, ZoomOutBySet> {


    Publication visit(ReadContext context, ZoomOutBySet form) throws NotFound, BadRequest, Panic;


    @Data
    class Publication {

        String set;

        @Unique
        String providerId;

        @Unique
        String modelSubId;

        @Unique
        String group;

        @Unique
        String name;

        @Unique
        String version;

        @Unique
        String visibility;

        @Unique
        String stability;

        String operatorId;

        Date beginTime;

        @MayHave
        Date endTime;
    }
}
