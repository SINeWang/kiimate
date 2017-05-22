package one.kii.kiimate.status.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;

import java.util.Date;

/**
 * Created by WangYanJiong on 22/05/2017.
 */
public interface SubscribeAssetApi {


    Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String insSubId;

        private String pubSet;

        private String stability;

        private String version;

        private String visibility;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        private String pubSet;

        private String ownerId;

        private String version;

        private String visibility;

        private Date beginTime;

    }

}
