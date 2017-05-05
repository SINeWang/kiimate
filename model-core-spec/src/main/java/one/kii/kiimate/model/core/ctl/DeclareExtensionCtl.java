package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
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
@RequestMapping("/api/v1/{" + OWNER_ID + "}/extension")
@CrossOrigin(value = "*")
public class DeclareExtensionCtl extends WriteController {

    public static final String OWNER_ID = "ownerId";

    @Autowired
    private DeclareExtensionApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseEntity<DeclareExtensionApi.CommitReceipt> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute DeclareExtensionApi.CommitForm form) {
        return commit(requestId, operatorId, ownerId, form);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    public ResponseEntity<DeclareExtensionApi.CommitReceipt> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestBody DeclareExtensionApi.CommitForm form) {
        return commit(requestId, operatorId, ownerId, form);
    }

    private ResponseEntity<DeclareExtensionApi.CommitReceipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            DeclareExtensionApi.CommitForm form) {
        try {
            WriteContext context = buildContext(requestId, operatorId, ownerId);
            return ErestResponse.created(requestId, api.commit(context, form));
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getFields());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys());
        }
    }


    @RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<DeclareExtensionApi.CancelReceipt> cancel(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute DeclareExtensionApi.CancelForm form) {

        try {
            WriteContext context = buildContext(requestId, operatorId, ownerId);

            return ErestResponse.created(requestId, api.cancel(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getFields());
        }
    }


}
