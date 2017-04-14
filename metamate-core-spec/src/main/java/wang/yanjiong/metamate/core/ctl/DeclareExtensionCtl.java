package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.receiver.WriteController;
import one.kii.summer.io.sender.ErestHeaders;
import one.kii.summer.io.sender.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1")
public class DeclareExtensionCtl extends WriteController {

    @Autowired
    private DeclareExtensionApi api;

    @RequestMapping(value = "/{ownerId}/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<DeclareExtensionApi.Receipt> execute(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @ModelAttribute DeclareExtensionApi.Form form) {

        try {
            WriteContext context = buildContext(requestId, operatorId, ownerId);

            return ErestResponse.created(requestId, api.declareExtension(context, form));
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getMessage());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKey());
        }
    }

}
