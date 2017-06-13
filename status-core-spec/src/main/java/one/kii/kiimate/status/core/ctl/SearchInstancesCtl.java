package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.SearchInstancesApi;
import one.kii.summer.asdf.api.SearchApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static one.kii.kiimate.status.core.ctl.SearchInstancesCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 02/05/2017.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/instances")
@CrossOrigin(origins = "*")
public class SearchInstancesCtl extends ReadController {

    public static final String OWNER_ID = "owner-id";

    @Autowired
    private SearchInstancesApi api;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SearchInstancesApi.Instance>> search(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestParam("q") String query) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        SearchInstancesApi.QueryForm form = new SearchInstancesApi.QueryForm();

        form.setGroup(query);

        return SearchApiCaller.sync(api, context, form);
    }


}
