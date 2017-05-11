package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.SearchSubscribersApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by WangYanJiong on 11/05/2017.
 */

@RestController
@RequestMapping("/api/v1/subscribers")
@CrossOrigin(origins = "*")
public class SearchSubscribersCtl extends ReadController {


    @Autowired
    private SearchSubscribersApi api;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("subscriberId") String subscriberId) {

        ReadContext context = buildContext(requestId, null, visitorId);


        SearchSubscribersApi.QueryForm form = new SearchSubscribersApi.QueryForm();

        form.setSubscriberId(subscriberId);

        List<SearchSubscribersApi.Subscribers> subscribers = api.search(context, form);
        return ErestResponse.ok(requestId, subscribers);
    }
}
