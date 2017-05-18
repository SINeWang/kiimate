package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.VisitExtensionApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.model.core.ctl.VisitExtensionCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/extensions")
@CrossOrigin(origins = "*")
public class VisitExtensionCtl extends ReadController {

    public static final String OWNER_ID = "ownerId";

    @Autowired
    private VisitExtensionApi api;

    @RequestMapping(value = "/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitExtensionApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitExtensionApi.Form form = new VisitExtensionApi.Form();
        form.setGroup(group);
        form.setName(name);
        form.setTree(tree);
        return ErestResponse.ok(requestId, api.visit(context, form));
    }


    @RequestMapping(value = "/{group}/{name:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitExtensionApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitExtensionApi.Form form = new VisitExtensionApi.Form();
        form.setGroup(group);
        form.setName(name);
        return ErestResponse.ok(requestId, api.visit(context, form));
    }

    @RequestMapping(value = "/{group:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitExtensionApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable("group") String group) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitExtensionApi.Form form = new VisitExtensionApi.Form();
        form.setGroup(group);
        return ErestResponse.ok(requestId, api.visit(context, form));
    }
}
