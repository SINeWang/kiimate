package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitAssetApi;
import one.kii.kiimate.status.core.api.VisitStatusApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitStatusCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 21/05/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/status", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitStatusCtl extends ReadController {

    public static final String OWNER_ID = "owner-id";

    public static final String PUB_SET = "pub-set";
    public static final String SUB_ID = "sub-id";

    public static final String VERSION = "version";

    public static final String STABILITY = "stability";

    public static final String GROUP = "group";

    public static final String NAME = "name";

    @Autowired
    private VisitAssetApi api;

    @Autowired
    private VisitStatusApi visitStatusApi;

    @RequestMapping(value = "/{" + PUB_SET + "}/{" + STABILITY + "}/{" + VERSION + ":.+}")
    public ResponseEntity<VisitAssetApi.Asset> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(PUB_SET) String pubSet,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);
        VisitAssetApi.PubSetForm form = new VisitAssetApi.PubSetForm();
        form.setPubSet(pubSet);
        form.setVersion(version);
        form.setStability(stability);
        try {
            return ErestResponse.ok(requestId, api.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + STABILITY + "}/{" + VERSION + ":.+}")
    public ResponseEntity<VisitAssetApi.Asset> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitAssetApi.GroupNameForm form = new VisitAssetApi.GroupNameForm();
        form.setGroup(group);
        form.setName(name);
        if (null != stability) {
            form.setStability(stability);
        }
        if (null != version) {
            form.setVersion(version);
        }
        try {
            return ErestResponse.ok(requestId, api.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }

    @RequestMapping(value = "{" + SUB_ID + "}")
    public ResponseEntity<VisitStatusApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) Long subId) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitStatusApi.Form form = new VisitStatusApi.Form();
        form.setOwnerId(ownerId);
        form.setSubId(subId);
        try {
            return ErestResponse.ok(requestId, visitStatusApi.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }


}
