package one.kii.kiimate.status.core.api;

import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.VisitDownWithXyz;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitStatusApi extends VisitApi<Map<String, Object>, ReadContext, VisitDownWithXyz> {


    Map<String, Object> visit(ReadContext context, VisitDownWithXyz form) throws NotFound, BadRequest, Panic;

}
