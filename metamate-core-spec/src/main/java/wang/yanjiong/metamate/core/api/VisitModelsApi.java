package wang.yanjiong.metamate.core.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/5/17.
 */

@RestController
@RequestMapping("/v1")
public interface VisitModelsApi {

    @RequestMapping(value = "/models/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestParam(value = "tag", defaultValue = "LATEST") String tag);
}
