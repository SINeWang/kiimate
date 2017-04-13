package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.erest.ErestHeaders;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitEntitiesApi;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1")
public class VisitEntitiesCtl {

    @Autowired
    private VisitEntitiesApi api;

    @RequestMapping(value = "/{ownerId}/entities/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {
        return ErestResponse.ok(requestId, api.readInstancesByGroupNameVersion(requestId, visitorId, ownerId, group, name, tree));
    }

    @RequestMapping(value = "/{ownerId}/entities/{group}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) {
        return ErestResponse.ok(requestId, api.readInstancesByGroupNameVersion(requestId, visitorId, ownerId, group));
    }
}
