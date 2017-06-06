package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitFatStatusApi;
import one.kii.kiimate.status.core.api.VisitRawStatusApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitStatusCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 03/06/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/status", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitStatusCtl extends ReadController {

    static final String OWNER_ID = "owner-id";

    static final String GROUP = "group";

    static final String NAME = "name";

    static final String TREE = "tree";

    static final String SUB_ID = "sub-id";

    @Autowired
    private VisitRawStatusApi api;

    @Autowired
    private VisitFatStatusApi fatStatusApi;

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + TREE + ":.+}")
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(TREE) String tree) {

        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitRawStatusApi.GroupNameTreeForm form = new VisitRawStatusApi.GroupNameTreeForm();
        form.setGroup(group);
        form.setName(name);
        if (null != name) {
            form.setName(name);
        }
        if (null != tree) {
            form.setTree(tree);
        }
        return VisitApiCaller.sync(api, context, form);

    }

    @RequestMapping(value = "/{" + SUB_ID + "}")
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) String subId) {

        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitFatStatusApi.StatusIdForm form = new VisitFatStatusApi.StatusIdForm();
        form.setId(subId);

        return VisitApiCaller.sync(fatStatusApi, context, form);
    }

}
