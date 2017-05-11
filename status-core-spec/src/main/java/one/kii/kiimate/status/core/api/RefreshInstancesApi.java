package one.kii.kiimate.status.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.util.MultiValueMap;

import java.util.List;


/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface RefreshInstancesApi {

    Receipt commit(WriteContext context, Form form) throws NotFound, Conflict;

    @Data
    class Form {
        String ownerId;
        String subId;
        MultiValueMap<String, String> map;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        String group;

        String name;

        String tree;

        List<Instance> instances;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {

        private String id;

        private String extId;

        private String intId;

        private String ownerId;

        private String operatorId;

        private String field;

        private String[] value;

    }

}
