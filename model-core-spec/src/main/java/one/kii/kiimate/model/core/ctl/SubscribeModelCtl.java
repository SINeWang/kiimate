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

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{subscriberId}/subscribe")
public class SubscribeModelCtl extends WriteController {

    @Autowired
    private SubscribeModelApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<SubscribeModelApi.Receipt> commit(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("subscriberId") String subscriberId,
            @ModelAttribute SubscribeModelApi.Form form) {
        try {

            WriteContext context = buildContext(requestId, operatorId, subscriberId);

            return ErestResponse.created(requestId, api.subscribe(context, form));
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys()[0]);
        }
    }
}
