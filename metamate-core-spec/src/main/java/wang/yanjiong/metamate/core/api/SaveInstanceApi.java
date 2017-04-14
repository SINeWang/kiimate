package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.context.exception.Conflict;
import one.kii.summer.context.exception.NotFound;
import one.kii.summer.context.io.WriteContext;
import org.springframework.util.MultiValueMap;

import java.util.List;


/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface SaveInstanceApi {

    String TREE_MASTER = "master";

    Receipt saveInstance(
            WriteContext context,
            Form form
    ) throws NotFound, Conflict;

    @Data
    class Form {
        String group;
        String name;
        String tree;
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
