package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitFatAssetApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.xyz.VisitDownWithXyz;
import one.kii.summer.xyz.VisitUpWithId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitAssetCtl.PROVIDER_ID;


/**
 * Created by WangYanJiong on 21/05/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + PROVIDER_ID + "}/asset", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitAssetCtl extends ReadController {

    public static final String PROVIDER_ID = "provider-id";

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
            @PathVariable(PROVIDER_ID) String providerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version) {
        ReadContext context = buildContext(requestId, providerId, visitorId);

        VisitDownWithXyz form = new VisitDownWithXyz();
        form.setGroup(group);
        form.setName(name);
        form.setStability(stability);
        form.setVersion(version);
        form.setProviderId(providerId);
        return VisitApiCaller.sync(api, context, form);
    }

    @RequestMapping(value = "/{" + ID + "}")
    public ResponseEntity<VisitFatAssetApi.Asset> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(PROVIDER_ID) String subscriberId,
            @PathVariable(ID) Long id) {
        ReadContext context = buildContext(requestId, subscriberId, visitorId);
        VisitUpWithId form = new VisitUpWithId();
        form.setId(id);
        form.setSubscriberId(subscriberId);
        return VisitApiCaller.sync(api, context, form);
    }

}
