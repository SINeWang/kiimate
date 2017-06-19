package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.SearchApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;

import java.util.Date;

/**
 * Created by WangYanJiong on 19/06/2017.
 */
public interface SearchGlimpseApi extends SearchApi<SearchGlimpseApi.Receipt, ReadContext, SearchGlimpseApi.Form> {


    Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound;

    @Data
    class Form {

        private String subSet;

        private String group;

        private String name;

        private String tree;

    }

    @Data
    class Receipt {

        private String id;

        private String subscriberId;

        private String subSet;

        private String group;

        private String name;

        private String tree;

        private Date beginTime;

        @MayHave
        private Date endTime;

    }

}
