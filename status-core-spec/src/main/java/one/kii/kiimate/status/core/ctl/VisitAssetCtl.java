package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitFatAssetApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.xyz.ViewUpWithId;
import one.kii.summer.xyz.ViewUpWithXyz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitAssetCtl.SUBSCRIBER_ID;


/**
 * Created by WangYanJiong on 21/05/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + SUBSCRIBER_ID + "}/asset", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitAssetCtl extends ReadController {

    public static final String SUBSCRIBER_ID = "subscriber-id";

    public static final String GROUP = "group";

    public static final String NAME = "name";

    public static final String STABILITY = "stability";

    public static final String VERSION = "version";

    public static final String ID = "id";

    @Autowired
    private VisitFatAssetApi api;


    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + STABILITY + "}/{" + VERSION + ":.+}")
    public ResponseEntity<VisitFatAssetApi.Asset> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(SUBSCRIBER_ID) String subscriberId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version) {
        ReadContext context = buildContext(requestId, subscriberId, visitorId);

        ViewUpWithXyz form = new ViewUpWithXyz();
        form.setGroup(group);
        form.setName(name);
        form.setStability(stability);
        form.setVersion(version);
        return VisitApiCaller.sync(api, context, form);
    }

    @RequestMapping(value = "/{" + ID + "}")
    public ResponseEntity<VisitFatAssetApi.Asset> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(SUBSCRIBER_ID) String subscriberId,
            @PathVariable(ID) Long id) {
        ReadContext context = buildContext(requestId, subscriberId, visitorId);
        ViewUpWithId form = new ViewUpWithId();
        form.setId(id);
        form.setSubscriberId(subscriberId);
        return VisitApiCaller.sync(api, context, form);
    }

}
