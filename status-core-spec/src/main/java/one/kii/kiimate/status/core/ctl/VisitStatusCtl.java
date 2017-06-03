package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitStatusApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitAssetCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 03/06/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/status", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitStatusCtl extends ReadController {

    public static final String OWNER_ID = "owner-id";

    public static final String GROUP = "group";

    public static final String NAME = "name";

    public static final String TREE = "tree";

    @Autowired
    private VisitStatusApi api;

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + TREE + ":.+}")
    public ResponseEntity<VisitStatusApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(TREE) String tree) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitStatusApi.GroupNameTreeForm form = new VisitStatusApi.GroupNameTreeForm();
        form.setGroup(group);
        form.setName(name);
        if (null != name) {
            form.setName(name);
        }
        if (null != tree) {
            form.setTree(tree);
        }
        try {
            return ErestResponse.ok(requestId, api.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKeys());
        }
    }

}
