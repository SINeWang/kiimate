package wang.yanjiong.metamate.core.api;

import lombok.Data;
import one.kii.summer.context.io.ReadContext;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitExtensionApi {

    String NAME_ROOT = "root";

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";

    Map<String, Object> readExtensionByGroupNameVersion(ReadContext context, Form form);

    Map<String, Object> readExtensionByGroupNameVersion(ReadContext context, SimpleForm form);

    Map<String, Object> readExtensionByGroupNameVersion(ReadContext context, TinyForm form);

    @Data
    class Form {
        String group;
        String name;
        String tree;
    }

    @Data
    class SimpleForm {
        String group;
        String name;
    }

    @Data
    class TinyForm {
        String group;
    }
}
