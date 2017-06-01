package one.kii.kiimate.status.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;

import java.util.Date;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface PublishAssetApi {


    Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String stability;

        private String providerId;

        private long subId;

        private String version;

        private String visibility;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        private long pubSet;

        private String group;

        private String name;

        private String providerId;

        private String version;

        private String visibility;

        private Date beginTime;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {

        private String field;

        private String[] value;
    }
}
