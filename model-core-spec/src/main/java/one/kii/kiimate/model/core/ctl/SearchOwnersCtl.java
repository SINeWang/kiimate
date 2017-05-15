package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.SearchOwnersApi;
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
@RequestMapping("/api/v1/owners")
@CrossOrigin(origins = "*")
public class SearchOwnersCtl extends ReadController {


    @Autowired
    private SearchOwnersApi searchOwnersApi;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @RequestParam("id") String id) {

        ReadContext context = buildContext(requestId, null, visitorId);


        SearchOwnersApi.QueryForm queryForm = new SearchOwnersApi.QueryForm();

        queryForm.setOwnerId(id);

        List<SearchOwnersApi.Owners> owners = searchOwnersApi.search(context, queryForm);
        return ErestResponse.ok(requestId, owners);
    }
}
