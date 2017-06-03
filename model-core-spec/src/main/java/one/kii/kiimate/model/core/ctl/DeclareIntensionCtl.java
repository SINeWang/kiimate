package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.DeclareIntensionApi;
import one.kii.summer.asdf.xi.CommitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.model.core.ctl.DeclareIntensionCtl.OWNER_ID;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/intensions")
@CrossOrigin(value = "*")
public class DeclareIntensionCtl extends WriteController {

    public static final String OWNER_ID = "owner-id";

    @Autowired
    private DeclareIntensionApi api;


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<DeclareIntensionApi.Receipt> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @ModelAttribute DeclareIntensionApi.Form form) {
        return commit(requestId, operatorId, ownerId, form);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<DeclareIntensionApi.Receipt> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @RequestBody DeclareIntensionApi.Form form) {
        return commit(requestId, operatorId, ownerId, form);
    }


    private ResponseEntity<DeclareIntensionApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            DeclareIntensionApi.Form form) {
        WriteContext context = buildContext(requestId, operatorId, ownerId);

        return CommitApiCaller.sync(api, context, form);
    }


}
