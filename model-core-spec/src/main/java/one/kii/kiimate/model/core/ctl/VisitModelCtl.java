package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.VisitModelApi;
import one.kii.summer.asdf.api.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 02/05/2017.
 */

@RestController
@RequestMapping(value = "/api/v1/models", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitModelCtl extends ReadController {

    public static final String PUB_SET = "pub-set";
    @Autowired
    private VisitModelApi api;

    @RequestMapping(value = "/{" + PUB_SET + ":.+}")
    public ResponseEntity<VisitModelApi.Model> search(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(PUB_SET) String set) {

        ReadContext context = buildContext(requestId, null, visitorId);

        VisitModelApi.VisitModelForm form = new VisitModelApi.VisitModelForm();

        form.setSet(set);

        return VisitApiCaller.sync(api, context, form);
    }
}
