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
public interface VisitExtensionApi {

    String NAME_ROOT = "root";

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";

    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree);

    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name);

    @RequestMapping(value = "/{ownerId}/extension/{group}", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group);
}
