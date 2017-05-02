package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitEntitiesApi;

import java.util.Map;

import static wang.yanjiong.metamate.core.api.VisitEntitiesApi.NAME_DEFAULT;
import static wang.yanjiong.metamate.core.api.VisitEntitiesApi.TREE_MASTER;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
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

    @RequestMapping(value = "/{ownerId}/entities/{group:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) {

        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitEntitiesApi.Form form = new VisitEntitiesApi.Form();
        form.setGroup(group);
        form.setName(NAME_DEFAULT);
        form.setTree(TREE_MASTER);
        return ErestResponse.ok(requestId, api.readInstancesByGroupNameTree(context, form));
    }
}
