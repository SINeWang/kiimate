package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.context.exception.Conflict;
import one.kii.summer.erest.ErestHeaders;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.DeclareIntensionApi;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1")
public class DeclareIntensionCtl {

    @Autowired
    DeclareIntensionApi api;

    @RequestMapping(value = "/{ownerId}/intension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<DeclareIntensionApi.Receipt> declareIntension(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @ModelAttribute DeclareIntensionApi.Form form) {
        try {
            return ErestResponse.created(requestId, api.declareIntension(requestId, operatorId, ownerId, form));
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKey());
        }

    }

}
