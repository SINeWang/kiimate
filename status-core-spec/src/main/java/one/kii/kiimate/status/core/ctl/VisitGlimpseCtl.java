package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitGlimpseApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitFatStatusCtl.PROVIDER_ID;

/**
 * Created by WangYanJiong on 17/06/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + PROVIDER_ID + "}", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitGlimpseCtl extends ReadController {

    public static final String SET = "glimpses-set";

    @Autowired
    private VisitGlimpseApi api;

    @RequestMapping("/glimpses/{" + SET + "}")
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String subscriberId,
            @PathVariable(PROVIDER_ID) String providerId,
            @PathVariable(SET) String set) {
        ReadContext context = buildContext(requestId, providerId, subscriberId);
        ZoomOutBySet zoom = new ZoomOutBySet();
        zoom.setSet(set);
        zoom.setProviderId(providerId);
        return VisitApiCaller.sync(api, context, zoom);
    }
}
