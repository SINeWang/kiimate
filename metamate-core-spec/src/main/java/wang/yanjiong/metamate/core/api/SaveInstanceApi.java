package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.context.exception.NotFound;
import org.springframework.util.MultiValueMap;

import java.util.List;


/**
 * Created by WangYanJiong on 26/03/2017.
 */
public interface SaveInstanceApi {

    String TREE_MASTER = "master";

    Receipt saveInstance(
            String requestId,
            String operatorId,
            String ownerId,
            String group,
            String name,
            String tree,
            MultiValueMap<String, String> map) throws NotFound;


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
