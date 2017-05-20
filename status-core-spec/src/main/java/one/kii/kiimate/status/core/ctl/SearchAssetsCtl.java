package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.SearchAssetsApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */

@RestController
@RequestMapping("/api/v1/search/assets")
@CrossOrigin(origins = "*")
public class SearchAssetsCtl extends ReadController {

    @Autowired
    private SearchAssetsApi api;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SearchAssetsApi.Assets>> exploreModels(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("q") String query,
            @RequestParam("ownerId") String ownerId) {

        ReadContext context = buildContext(requestId, ownerId, visitorId);

        SearchAssetsApi.QueryForm form = new SearchAssetsApi.QueryForm();

        form.setQuery(query);

        List<SearchAssetsApi.Assets> models = api.search(context, form);
        return ErestResponse.ok(requestId, models);
    }
}
