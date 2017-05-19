package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.SearchExtensionsApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static one.kii.kiimate.model.core.ctl.SearchExtensionsCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 02/05/2017.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/extensions")
@CrossOrigin(origins = "*")
public class SearchExtensionsCtl extends ReadController {

    public static final String OWNER_ID = "owner-id";


    @Autowired
    private SearchExtensionsApi searchExtensionsApi;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestParam("group") String group) {

        ReadContext context = buildContext(requestId, ownerId, visitorId);


        SearchExtensionsApi.QueryForm queryForm = new SearchExtensionsApi.QueryForm();

        queryForm.setOwnerId(ownerId);
        queryForm.setGroup(group);

        List<SearchExtensionsApi.Extension> extensions = searchExtensionsApi.search(context, queryForm);
        return ErestResponse.ok(requestId, extensions);
    }
}
