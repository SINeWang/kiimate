package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.SnapshotModelApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1")
public class SnapshotModelCtl extends WriteController {

    @Autowired
    private SnapshotModelApi api;


    @RequestMapping(value = "/{ownerId}/snapshot/{group:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<SnapshotModelApi.Receipt> snapshot(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @ModelAttribute SnapshotModelApi.Form form) {
        try {

            WriteContext context = buildContext(requestId, operatorId, ownerId);

            form.setGroup(group);
            return ErestResponse.created(requestId, api.snapshot(context, form));
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getFields());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys()[0]);
        }
    }
}
