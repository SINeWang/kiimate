package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitIntensionsApi;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class VisitIntensionCtl extends ReadController {


    private static final String NAME_ROOT = "root";

    private static final String TREE_MASTER = "master";

    @Autowired
    private VisitIntensionsApi api;

    @RequestMapping(value = "/{ownerId}/extensions/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitIntensionsApi.Extension> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {
        return getExtensionResponseEntity(requestId, ownerId, visitorId, group, name, tree);
    }

    @RequestMapping(value = "/{ownerId}/extensions/{group}/{name:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitIntensionsApi.Extension> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name) {
        return getExtensionResponseEntity(requestId, ownerId, visitorId, group, name, TREE_MASTER);
    }

    @RequestMapping(value = "/{ownerId}/extensions/{group:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitIntensionsApi.Extension> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group) {
        return getExtensionResponseEntity(requestId, ownerId, visitorId, group, NAME_ROOT, TREE_MASTER);
    }

    private ResponseEntity<VisitIntensionsApi.Extension> getExtensionResponseEntity(
            String requestId,
            String ownerId,
            String visitorId,
            String group,
            String name,
            String tree) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitIntensionsApi.Form form = new VisitIntensionsApi.Form();
        form.setGroup(group);
        form.setName(name);
        form.setTree(tree);
        try {
            return ErestResponse.ok(requestId, api.readIntensionsByGroupNameVersion(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }
}
