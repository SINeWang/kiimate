package one.kii.kiimate.status.core.api;

import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomInById;
import one.kii.summer.zoom.ZoomOutBySet;

import java.util.Map;

/**
 * Created by WangYanJiong on 20/6/17.
 */

public interface VisitRawStatusApi extends VisitApi<Map<String, Object>, ReadContext, ZoomOutBySet> {


    Map<String, Object> visit(ReadContext context, ZoomOutBySet form) throws NotFound, BadRequest, Panic;

}
