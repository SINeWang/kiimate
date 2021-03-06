package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.summer.asdf.api.CommitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.model.core.ctl.DeclareExtensionCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/extensions")
@CrossOrigin(value = "*")
public class DeclareExtensionCtl extends WriteController {

    public static final String OWNER_ID = "owner-id";

    @Autowired
    private DeclareExtensionApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<?> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute DeclareExtensionApi.CommitForm form) {
        return commit(requestId, ownerId, operatorId, form);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<?> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestBody DeclareExtensionApi.CommitForm form) {
        return commit(requestId, ownerId, operatorId, form);
    }

    private ResponseEntity<?> commit(
            String requestId,
            String ownerId,
            String operatorId,
            DeclareExtensionApi.CommitForm form) {
        WriteContext context = buildContext(requestId, ownerId, operatorId);
        return CommitApiCaller.sync(api, context, form);
    }


}
