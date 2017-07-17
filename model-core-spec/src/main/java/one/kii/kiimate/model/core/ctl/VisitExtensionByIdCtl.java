package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.VisitExtensionByIdApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import one.kii.summer.zoom.ZoomInById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 27/06/17.
 */

@RestController
@RequestMapping("/api/v1/extensions")
@CrossOrigin(origins = "*")
public class VisitExtensionByIdCtl extends ReadController {

    static final String ID = "id";

    @Autowired
    private VisitExtensionByIdApi api;


    @RequestMapping(value = "/{" + ID + "}", method = RequestMethod.GET)
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(ID) String id) {

        ReadContext context = buildContext(requestId, null, visitorId);

        ZoomInById form = new ZoomInById();
        form.setId(id);
        return VisitApiCaller.sync(api, context, form);
    }
}
