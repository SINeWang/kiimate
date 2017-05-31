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
@RequestMapping("/api/v1/models")
@CrossOrigin(origins = "*")
public class SearchModelsCtl extends ReadController {

    @Autowired
    private SearchModelsApi searchModelsApi;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SearchModelsApi.Models>> exploreModels(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("q") String query) {

        ReadContext context = buildContext(requestId, null, visitorId);

        SearchModelsApi.QueryModelsForm form = new SearchModelsApi.QueryModelsForm();

        form.setGroup(query);

        List<SearchModelsApi.Models> models = searchModelsApi.search(context, form);
        return ErestResponse.ok(requestId, models);
    }
}
