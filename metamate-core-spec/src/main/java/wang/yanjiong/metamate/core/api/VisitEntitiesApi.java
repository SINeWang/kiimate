package wang.yanjiong.metamate.core.api;

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

    Map<String, Object> readInstancesByGroupNameVersion(
            String requestId,
            String visitorId,
            String ownerId,
            String group,
            String name,
            String tree);

    Map<String, Object> readInstancesByGroupNameVersion(
            String requestId,
            String visitorId,
            String ownerId,
            String group);
}
