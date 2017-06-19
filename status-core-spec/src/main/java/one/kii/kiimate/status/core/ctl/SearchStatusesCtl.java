package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.SearchStatusesApi;
import one.kii.summer.asdf.api.SearchApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */

@RestController
@RequestMapping("/api/v1/statuses")
@CrossOrigin(origins = "*")
public class SearchStatusesCtl extends ReadController {

    @Autowired
    private SearchStatusesApi api;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SearchStatusesApi.Statuses>> search(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("q") String query,
            @RequestParam("ownerId") String ownerId) {

        ReadContext context = buildContext(requestId, ownerId, visitorId);

        SearchStatusesApi.QueryForm form = new SearchStatusesApi.QueryForm();

        form.setQuery(query);

        return SearchApiCaller.sync(api, context, form);
    }
}
