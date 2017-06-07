package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitRawStatusApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.xyz.ViewDownWithXyz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitStatusCtl.PROVIDER_ID;


/**
 * Created by WangYanJiong on 03/06/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + PROVIDER_ID + "}/status", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitStatusCtl extends ReadController {

    static final String PROVIDER_ID = "provider_id";

    static final String GROUP = "group";

    static final String NAME = "name";

    static final String STABILITY = "stability";

    static final String VERSION = "version";

    static final String SET = "set";

    @Autowired
    private VisitRawStatusApi api;



    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + STABILITY + "}/{" + VERSION + ":.+}")
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(PROVIDER_ID) String providerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version) {

        ReadContext context = buildContext(requestId, providerId, visitorId);

        ViewDownWithXyz form = new ViewDownWithXyz();
        form.setProviderId(providerId);
        form.setGroup(group);
        form.setName(name);
        form.setStability(stability);
        form.setVersion(version);
        return VisitApiCaller.sync(api, context, form);

    }


}
