package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.VisitExtensionApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.xyz.VisitUpWithXyz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.model.core.ctl.VisitExtensionCtl.PROVIDER_ID;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + PROVIDER_ID + "}/extensions")
@CrossOrigin(origins = "*")
public class VisitExtensionCtl extends ReadController {

    static final String PROVIDER_ID = "owner-id";

    static String NAME_ROOT = "root";

    static String TREE_MASTER = "master";

    @Autowired
    private VisitExtensionApi api;

    @RequestMapping(value = "/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitExtensionApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(PROVIDER_ID) String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);
        VisitUpWithXyz form = new VisitUpWithXyz();
        form.setGroup(group);
        form.setName(name);
        form.setTree(tree);
        return VisitApiCaller.sync(api, context, form);
    }


    @RequestMapping(value = "/{group}/{name:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitExtensionApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(PROVIDER_ID) String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitUpWithXyz form = new VisitUpWithXyz();
        form.setGroup(group);
        form.setName(name);
        form.setTree(TREE_MASTER);

        return VisitApiCaller.sync(api, context, form);

    }

    @RequestMapping(value = "/{group:.+}", method = RequestMethod.GET)
    public ResponseEntity<VisitExtensionApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(PROVIDER_ID) String ownerId,
            @PathVariable("group") String group) {

        ReadContext context = buildContext(requestId, visitorId, ownerId);

        VisitUpWithXyz form = new VisitUpWithXyz();
        form.setGroup(group);
        form.setName(NAME_ROOT);
        form.setTree(TREE_MASTER);
        return VisitApiCaller.sync(api, context, form);
    }
}
