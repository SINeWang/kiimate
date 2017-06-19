package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitGlimpseApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.zoom.ZoomInById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitGlimpseCtl.ASSET_ID;
import static one.kii.kiimate.status.core.ctl.VisitStatusCtl.PROVIDER_ID;

/**
 * Created by WangYanJiong on 17/06/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + PROVIDER_ID + "}/glimpse/{" + ASSET_ID + "}", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitGlimpseCtl extends ReadController {

    public static final String ASSET_ID = "asset-id";

    @Autowired
    private VisitGlimpseApi api;

    private ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String subscriberId,
            @PathVariable(PROVIDER_ID) String providerId,
            @PathVariable(ASSET_ID) Long id) {
        ReadContext context = buildContext(requestId, providerId, subscriberId);
        ZoomInById zoom = new ZoomInById();
        zoom.setId(id);
        zoom.setSubscriberId(subscriberId);
        return VisitApiCaller.sync(api, context, zoom);
    }
}
