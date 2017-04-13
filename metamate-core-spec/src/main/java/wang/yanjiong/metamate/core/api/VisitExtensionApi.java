package wang.yanjiong.metamate.core.api;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

public interface VisitExtensionApi {

    String NAME_ROOT = "root";

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";

    ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            String requestId,
            String visitorId,
            String ownerId,
            String group,
            String name,
            String tree);

    ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            String requestId,
            String visitorId,
            String ownerId,
            String group,
            String name);

    ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            String requestId,
            String visitorId,
            String ownerId,
            String group);
}
