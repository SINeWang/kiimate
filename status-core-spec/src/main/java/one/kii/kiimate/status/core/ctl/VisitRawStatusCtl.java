package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitRawStatusApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.zoom.ZoomOutByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitFatStatusCtl.PROVIDER_ID;


/**
 * Created by WangYanJiong on 20/06/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + PROVIDER_ID + "}/raw-status", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitRawStatusCtl extends ReadController {

    static final String PROVIDER_ID = "provider_id";

    static final String GROUP = "group";

    static final String NAME = "name";

    static final String STABILITY = "stability";

    static final String VERSION = "version";

    @Autowired
    private VisitRawStatusApi api;

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + STABILITY + "}/{" + VERSION + ":.+}", produces = "application/json")
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(PROVIDER_ID) String providerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version) {

        ReadContext context = buildContext(requestId, providerId, visitorId);

        ZoomOutByName form = new ZoomOutByName();
        form.setProviderId(providerId);
        form.setGroup(group);
        form.setName(name);
        form.setStability(stability);
        form.setVersion(version);
        return VisitApiCaller.sync(api, context, form);

    }


}
