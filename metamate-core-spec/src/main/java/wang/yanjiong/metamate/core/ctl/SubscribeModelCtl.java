package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.context.exception.Conflict;
import one.kii.summer.context.io.WriteContext;
import one.kii.summer.context.io.WriteController;
import one.kii.summer.erest.ErestHeaders;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1")
public class SubscribeModelCtl extends WriteController {

    @Autowired
    private SubscribeModelApi api;

    @RequestMapping(value = "/{subscriberId}/subscribe", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<SubscribeModelApi.Receipt> subscribe(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("subscriberId") String subscriberId,
            @ModelAttribute SubscribeModelApi.Form form) {
        try {

            WriteContext context = buildContext(requestId, operatorId, subscriberId);

            return ErestResponse.created(requestId, api.subscribe(context, form));
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKey());
        }
    }
}
