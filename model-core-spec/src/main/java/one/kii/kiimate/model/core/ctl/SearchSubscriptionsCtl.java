package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.SearchSubscriptionsApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static one.kii.kiimate.model.core.ctl.SearchSubscriptionsCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 02/05/2017.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/subscriptions")
@CrossOrigin(origins = "*")
public class SearchSubscriptionsCtl extends ReadController {
    public static final String OWNER_ID = "ownerId";

    @Autowired
    private SearchSubscriptionsApi searchSubscriptionsApi;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SearchSubscriptionsApi.Subscriptions>> search(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestParam("q") String query) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        SearchSubscriptionsApi.QueryForm form = new SearchSubscriptionsApi.QueryForm();

        form.setGroup(query);

        List<SearchSubscriptionsApi.Subscriptions> subscriptions = searchSubscriptionsApi.search(context, form);
        return ErestResponse.ok(requestId, subscriptions);
    }


}
