package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.SearchGlimpseIntensionsApi;
import one.kii.summer.asdf.api.SearchApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitFatStatusCtl.PROVIDER_ID;

/**
 * Created by WangYanJiong on 20/06/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + PROVIDER_ID + "}", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitGlimpseIntensionsCtl extends ReadController {

    public static final String SET = "glimpses-set";

    @Autowired
    private SearchGlimpseIntensionsApi api;

    @RequestMapping("/glimpses/{" + SET + "}/intensions")
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String subscriberId,
            @PathVariable(PROVIDER_ID) String providerId,
            @PathVariable(SET) String set) {
        ReadContext context = buildContext(requestId, providerId, subscriberId);
        ZoomOutBySet zoom = new ZoomOutBySet();
        zoom.setSet(set);
        zoom.setProviderId(providerId);
        return SearchApiCaller.sync(api, context, zoom);
    }
}
