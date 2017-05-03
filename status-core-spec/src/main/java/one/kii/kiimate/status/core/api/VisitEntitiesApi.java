package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

@RestController
@RequestMapping("/v1")
public interface VisitEntitiesApi {

    String NAME_DEFAULT = "default";

    String TREE_MASTER = "master";

    Receipt readInstancesByGroupNameTree(ReadContext context, Form form);

    @Data
    class Form {
        String group;
        String name;
        String tree;
    }

    @Data
    class Receipt {
        String ownerId;
        String group;
        String name;
        String tree;
        Map<String, Object> instances;
    }
}
