package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.SearchApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomOutBySet;

import java.util.List;

/**
 * Created by WangYanJiong on 20/6/17.
 */

public interface SearchGlimpseIntensionsApi extends SearchApi<SearchGlimpseIntensionsApi.Intension, ReadContext, ZoomOutBySet> {


    List<Intension> search(ReadContext context, ZoomOutBySet form) throws BadRequest, Panic;


    @Data
    class Intension {

        String field;

    }

}
