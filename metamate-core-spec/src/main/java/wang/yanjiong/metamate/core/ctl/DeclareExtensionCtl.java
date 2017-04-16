package wang.yanjiong.metamate.core.ctl;

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
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;

import static wang.yanjiong.metamate.core.ctl.DeclareExtensionCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1/{" + OWNER_ID + "}/extension")
public class DeclareExtensionCtl extends WriteController {

    public final static String OWNER_ID = "owner-id";

    @Autowired
    private DeclareExtensionApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<DeclareExtensionApi.CommitReceipt> commit(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute DeclareExtensionApi.CommitForm form) {

        try {
            WriteContext context = buildContext(requestId, operatorId, ownerId);

            return ErestResponse.created(requestId, api.commit(context, form));
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getFields());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys()[0]);
        }
    }


    @RequestMapping(method = RequestMethod.PATCH, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<DeclareExtensionApi.CancelReceipt> cancel(
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
