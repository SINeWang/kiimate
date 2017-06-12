package one.kii.kiimate.status.core.api;

import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomInById;

import java.util.Map;

/**
 * Created by WangYanJiong on 7/6/17.
 */

public interface VisitAssetApi extends VisitApi<Map<String, Object>, ReadContext, ZoomInById> {


    Map<String, Object> visit(ReadContext context, ZoomInById form) throws NotFound, BadRequest, Panic;

}
