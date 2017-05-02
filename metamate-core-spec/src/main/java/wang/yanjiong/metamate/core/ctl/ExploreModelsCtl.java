package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.receiver.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.ExploreModelsApi;

import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ExploreModelsCtl {

    @Autowired
    private ExploreModelsApi exploreModelsApi;

    @RequestMapping(value = "/explore/publishers", method = RequestMethod.GET)
    public ResponseEntity<?> explorePublishers(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("q") String query) {
        List<ExploreModelsApi.Provider> providers = exploreModelsApi.getProviders(query);
        return ErestResponse.ok(requestId, providers);
    }
}
