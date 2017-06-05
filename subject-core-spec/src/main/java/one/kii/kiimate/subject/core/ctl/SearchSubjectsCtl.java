package one.kii.kiimate.subject.core.ctl;

import one.kii.kiimate.subject.core.api.SearchSubjectsApi;
import one.kii.summer.asdf.xi.SearchApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static one.kii.kiimate.subject.core.ctl.SearchSubjectsCtl.ACCESS_TYPE;
import static one.kii.kiimate.subject.core.ctl.SearchSubjectsCtl.OBJECT_TYPE;

/**
 * Created by WangYanJiong on 15/05/2017.
 */
@RestController
@RequestMapping("/api/v1/subjects/{" + OBJECT_TYPE + "}/{" + ACCESS_TYPE + "}")
@CrossOrigin(origins = "*")
public class SearchSubjectsCtl extends ReadController {

    public static final String OBJECT_TYPE = "object-type";

    public static final String ACCESS_TYPE = "access-type";

    @Autowired
    private SearchSubjectsApi api;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<SearchSubjectsApi.Subjects>> search(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OBJECT_TYPE) String objectType,
            @PathVariable(ACCESS_TYPE) String accessType,
            @RequestParam("q") String q) {

        ReadContext context = buildContext(requestId, null, visitorId);

        SearchSubjectsApi.Form form = new SearchSubjectsApi.Form();
        form.setGroup(q);
        form.setAccessType(SearchSubjectsApi.AccessType.valueOf(accessType.toUpperCase()));
        form.setObjectType(SearchSubjectsApi.ObjectType.valueOf(objectType.toUpperCase()));

        return SearchApiCaller.sync(api, context, form);
    }
}
