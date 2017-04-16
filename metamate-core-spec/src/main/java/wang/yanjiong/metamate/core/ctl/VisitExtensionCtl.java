package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
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
public class VisitExtensionCtl extends ReadController {

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

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitExtensionApi.Form form = new VisitExtensionApi.Form();
        form.setGroup(group);
        form.setName(name);
        form.setTree(tree);

        return ErestResponse.ok(requestId, api.readExtensionByGroupNameVersion(context, form));
    }


    @RequestMapping(value = "/{ownerId}/extension/{group}/{name}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitExtensionApi.SimpleForm form = new VisitExtensionApi.SimpleForm();
        form.setGroup(group);
        form.setName(name);

        return ErestResponse.ok(requestId, api.readExtensionByGroupNameVersion(context, form));
    }

    @RequestMapping(value = "/{ownerId}/extension/{group}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitExtensionApi.TinyForm form = new VisitExtensionApi.TinyForm();
        form.setGroup(group);

        return ErestResponse.ok(requestId, api.readExtensionByGroupNameVersion(context, form));
    }
}
