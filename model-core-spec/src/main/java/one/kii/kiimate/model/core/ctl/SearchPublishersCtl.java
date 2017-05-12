package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.SearchModelsApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */

@RestController
@RequestMapping("/api/v1/explore/publishers")
@CrossOrigin(origins = "*")
public class SearchPublishersCtl extends ReadController {

    @Autowired
    private SearchModelsApi searchModelsApi;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SearchModelsApi.Provider>> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("q") String query) {
        ReadContext context = buildContext(requestId, null, visitorId);

        SearchModelsApi.QueryProvidersForm form = new SearchModelsApi.QueryProvidersForm();

        form.setQuery(query);

        List<SearchModelsApi.Provider> providers = searchModelsApi.search(context, form);
        return ErestResponse.ok(requestId, providers);
    }


}
