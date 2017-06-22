package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.DeclareInstanceApi;
import one.kii.summer.asdf.api.CommitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.DeclareInstanceCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 21/6/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/instances")
@CrossOrigin(origins = "*")
public class DeclareInstanceCtl extends WriteController {

    public static final String OWNER_ID = "owner-id";


    @Autowired
    private DeclareInstanceApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<?> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute DeclareInstanceApi.Form form) {
        return commit(requestId, operatorId, ownerId, form);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestBody DeclareInstanceApi.Form form) {
        return commit(requestId, operatorId, ownerId, form);
    }

    private ResponseEntity<?> commit(
            String requestId,
            String operatorId,
            String ownerId,
            DeclareInstanceApi.Form form) {
        WriteContext context = buildContext(requestId, ownerId, operatorId);

        return CommitApiCaller.sync(api, context, form);
    }
}
