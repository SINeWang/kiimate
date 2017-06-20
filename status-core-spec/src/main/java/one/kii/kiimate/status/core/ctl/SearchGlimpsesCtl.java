package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.SearchGlimpsesApi;
import one.kii.kiimate.status.core.api.SearchStatusesApi;
import one.kii.summer.asdf.api.SearchApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 20/05/2017.
 */

@RestController
@RequestMapping("/api/v1/glimpses")
@CrossOrigin(origins = "*")
public class SearchGlimpsesCtl extends ReadController {

    @Autowired
    private SearchGlimpsesApi api;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> search(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("q") String query,
            @RequestParam("providerId") String providerId) {

        ReadContext context = buildContext(requestId, providerId, visitorId);

        SearchGlimpsesApi.Form form = new SearchGlimpsesApi.Form();

        form.setGroup(query);


        return SearchApiCaller.sync(api, context, form);
    }
}
