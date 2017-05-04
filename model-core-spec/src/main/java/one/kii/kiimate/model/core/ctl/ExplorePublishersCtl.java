package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.ExploreModelsApi;
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
public class ExplorePublishersCtl extends ReadController {

    @Autowired
    private ExploreModelsApi exploreModelsApi;

    @RequestMapping(value = "/explore/publishers", method = RequestMethod.GET)
    public ResponseEntity<List<ExploreModelsApi.Provider>> explorePublishers(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("q") String query) {
        ReadContext context = buildContext(requestId, null, visitorId);

        ExploreModelsApi.QueryProvidersForm form = new ExploreModelsApi.QueryProvidersForm();

        form.setQuery(query);

        List<ExploreModelsApi.Provider> providers = exploreModelsApi.queryProviders(context, form);
        return ErestResponse.ok(requestId, providers);
    }


}
