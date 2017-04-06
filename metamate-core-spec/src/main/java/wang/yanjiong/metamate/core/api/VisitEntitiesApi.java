package wang.yanjiong.metamate.core.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

@RestController
@RequestMapping("/v1")
public interface VisitEntitiesApi {

    @RequestMapping(value = "/{ownerId}/entities/{group}/{name}/{version:.+}", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("version") String version,
            @RequestParam(value = "tag", defaultValue = "LATEST") String tag);
}
