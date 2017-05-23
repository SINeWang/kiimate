package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitAssetsApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitAssetsCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 21/05/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/assets", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitAssetsCtl extends ReadController {

    public static final String OWNER_ID = "owner-id";

    public static final String PUB_SET = "pub-set";

    public static final String VERSION = "version";

    public static final String GROUP = "group";

    public static final String NAME = "name";

    public static final String TREE = "tree";

    @Autowired
    private VisitAssetsApi api;

    @RequestMapping(value = "/{" + PUB_SET + "}/{" + VERSION + ":.+}")
    public ResponseEntity<VisitAssetsApi.Assets> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(PUB_SET) String pubSet,
            @PathVariable(VERSION) String version) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);
        VisitAssetsApi.PubSetForm form = new VisitAssetsApi.PubSetForm();
        form.setPubSet(pubSet);
        form.setVersion(version);
        try {
            return ErestResponse.ok(requestId, api.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + TREE + ":.+}")
    public ResponseEntity<VisitAssetsApi.Assets> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(TREE) String tree) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitAssetsApi.GroupNameForm form = new VisitAssetsApi.GroupNameForm();
        form.setGroup(group);
        form.setName(name);
        form.setTree(tree);
        try {
            return ErestResponse.ok(requestId, api.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }

}
