package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.SearchApi;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 19/06/2017.
 */
public interface SearchGlimpseApi extends SearchApi<SearchGlimpseApi.Receipt, ReadContext, SearchGlimpseApi.Form> {


    List<Receipt> search(ReadContext context, Form form) throws BadRequest, Panic;

    @Data
    class Form {

        private String group;

    }

    @Data
    class Receipt {

        private String providerId;

        private String set;

        private String group;

        private String name;

        private String stability;

        private String version;

        private String visibility;

        private Date beginTime;

        @MayHave
        private Date endTime;

    }

}
