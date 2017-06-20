package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.SearchApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.OutsideView;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/06/2017.
 */
public interface SearchGlimpsesApi extends SearchApi<SearchGlimpsesApi.Glimpse, ReadContext, SearchGlimpsesApi.Form> {


    List<Glimpse> search(ReadContext context, Form form) throws BadRequest, Panic;

    @Data
    class Form {

        private String group;
    }

    @Data
    class Glimpse {
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
