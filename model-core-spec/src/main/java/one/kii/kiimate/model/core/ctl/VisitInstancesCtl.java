package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.VisitInstancesApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static one.kii.kiimate.model.core.ctl.VisitExtensionCtl.OWNER_ID;
import static one.kii.kiimate.model.core.ctl.VisitInstancesCtl.SUB_ID;

/**
 * Created by WangYanJiong on 11/05/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/instances/{" + SUB_ID + "}", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitInstancesCtl extends ReadController {

    public static final String OWNER_ID = "ownerId";

    public static final String SUB_ID = "subId";

    @Autowired
    private VisitInstancesApi api;

    @RequestMapping()
    public ResponseEntity<List<VisitInstancesApi.Instance>> visit(
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
