package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.context.io.ReadContext;
import one.kii.summer.context.io.ReadController;
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
public class VisitEntitiesCtl extends ReadController {

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

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitEntitiesApi.Form form = new VisitEntitiesApi.Form();
        form.setTree(tree);
        form.setGroup(group);
        form.setName(name);
        return ErestResponse.ok(requestId, api.readInstancesByGroupNameTree(context, form));
    }

    @RequestMapping(value = "/{ownerId}/entities/{group}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitEntitiesApi.SimpleForm form = new VisitEntitiesApi.SimpleForm();
        form.setGroup(group);
        return ErestResponse.ok(requestId, api.readInstancesByGroup(context, form));
    }
}
