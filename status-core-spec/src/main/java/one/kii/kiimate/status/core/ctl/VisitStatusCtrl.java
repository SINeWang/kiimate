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

import static one.kii.kiimate.status.core.ctl.VisitStatusCtrl.OWNER_ID;
import static one.kii.kiimate.status.core.ctl.VisitStatusCtrl.SUB_ID;


/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/status/{" + SUB_ID + "}", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitStatusCtrl extends ReadController {

    public static final String OWNER_ID = "owner-id";

    public static final String SUB_ID = "sub-id";


    @Autowired
    private VisitStatusApi api;

    @RequestMapping
    public ResponseEntity<VisitStatusApi.Receipt> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) long subId) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitStatusApi.Form form = new VisitStatusApi.Form();
        form.setOwnerId(ownerId);
        form.setSubId(subId);
        try {
            return ErestResponse.ok(requestId, api.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }

}
