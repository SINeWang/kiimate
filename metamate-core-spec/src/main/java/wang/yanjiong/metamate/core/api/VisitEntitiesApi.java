package wang.yanjiong.metamate.core.api;

import one.kii.summer.erest.ErestHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

@RestController
@RequestMapping("/v1")
public interface VisitEntitiesApi {

    String NAME_DEFAULT = "default";

    String TREE_MASTER = "master";

    @RequestMapping(value = "/{ownerId}/entities/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree);

    @RequestMapping(value = "/{ownerId}/entities/{group:.+}", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group);
}
