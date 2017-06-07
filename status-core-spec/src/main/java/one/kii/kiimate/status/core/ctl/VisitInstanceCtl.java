package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitInstanceApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.xyz.ViewUpWithId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitInstanceCtl.SUBSCRIBER_ID;

/**
 * Created by WangYanJiong on 07/06/2017.
 */

@RestController
@RequestMapping(value = "/api/v1/{" + SUBSCRIBER_ID + "}/instance", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitInstanceCtl extends ReadController {

    static final String SUBSCRIBER_ID = "subscriber-id";

    static final String ID = "id";

    @Autowired
    private VisitInstanceApi api;

    @RequestMapping(value = "/{" + ID + "}")
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(SUBSCRIBER_ID) String subscriberId,
            @PathVariable(ID) String id) {

        ReadContext context = buildContext(requestId, subscriberId, visitorId);

        ViewUpWithId form = new ViewUpWithId();
        form.setId(Long.valueOf(id));
        form.setSubscriberId(subscriberId);

        return VisitApiCaller.sync(api, context, form);
    }

}
