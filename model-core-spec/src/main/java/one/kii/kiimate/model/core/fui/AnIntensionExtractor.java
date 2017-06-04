package one.kii.kiimate.model.core.fui;

import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnIntensionExtractor {

    IntensionDai.Record extract(WriteContext context, DeclareIntensionApi.Form form) throws BadRequest, NotFound;

}
