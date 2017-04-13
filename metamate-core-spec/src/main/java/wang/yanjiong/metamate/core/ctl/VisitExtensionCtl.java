package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.erest.ErestHeaders;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitExtensionApi;

import java.util.Map;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1")
public class VisitExtensionCtl {

    @Autowired
    private VisitExtensionApi api;

    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {
        return ErestResponse.ok(requestId, api.readIntensionsByGroupNameVersion(requestId, visitorId, ownerId, group, name, tree));
    }


    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name) {
        return ErestResponse.ok(requestId, api.readIntensionsByGroupNameVersion(requestId, visitorId, ownerId, group, name));
    }

    @RequestMapping(value = "/{ownerId}/extension/{group}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) {
        return ErestResponse.ok(requestId, api.readIntensionsByGroupNameVersion(requestId, visitorId, ownerId, group));
    }
}
