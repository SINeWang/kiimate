package wang.yanjiong.metamate.core.api;

import lombok.Data;
import one.kii.summer.context.io.ReadContext;
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

    Map<String, Object> readInstancesByGroupNameTree(
            ReadContext context, Form form);

    Map<String, Object> readInstancesByGroup(
            ReadContext context, SimpleForm form);

    @Data
    class Form {
        String group;
        String name;
        String tree;
    }

    @Data
    class SimpleForm {
        String group;
    }
}
