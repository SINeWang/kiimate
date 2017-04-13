package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.erest.ErestHeaders;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitIntensionsApi;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1")
public class VisitIntensionCtl {

    @Autowired
    private VisitIntensionsApi api;

    @RequestMapping(value = "/{ownerId}/intension/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitIntensionsApi.Extension> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {
        return ErestResponse.ok(requestId, api.readIntensionsByGroupNameVersion(requestId, visitorId, ownerId, group, name, tree));
    }
}
