package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.SubscribeModelApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.model.core.ctl.SubscribeModelCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/subscriptions/models")
@CrossOrigin(origins = "*")
public class SubscribeModelCtl extends WriteController {

    public static final String OWNER_ID = "owner-id";


    @Autowired
    private SubscribeModelApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<SubscribeModelApi.Receipt> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute SubscribeModelApi.Form form) {
        return commit(requestId, operatorId, ownerId, form);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<SubscribeModelApi.Receipt> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestBody SubscribeModelApi.Form form) {
        return commit(requestId, operatorId, ownerId, form);
    }

    private ResponseEntity<SubscribeModelApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            SubscribeModelApi.Form form) {
        try {

            WriteContext context = buildContext(requestId, ownerId, operatorId);

            return ErestResponse.created(requestId, api.commit(context, form));
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys());
        }
    }
}
