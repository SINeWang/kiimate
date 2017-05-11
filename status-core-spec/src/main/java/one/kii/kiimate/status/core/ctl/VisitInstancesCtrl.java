package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitInstancesApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitInstancesCtrl.OWNER_ID;
import static one.kii.kiimate.status.core.ctl.VisitInstancesCtrl.SUB_ID;


/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/instances/{" + SUB_ID + "}", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitInstancesCtrl extends ReadController {

    public static final String OWNER_ID = "ownerId";

    public static final String SUB_ID = "subId";


    @Autowired
    private VisitInstancesApi api;

    @RequestMapping
    public ResponseEntity<VisitInstancesApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) String subId) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitInstancesApi.Form form = new VisitInstancesApi.Form();
        form.setSubId(subId);
        try {
            return ErestResponse.ok(requestId, api.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }

}
