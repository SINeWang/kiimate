package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.summer.asdf.api.CommitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.model.core.ctl.PublishModelCtl.OWNER_ID;
import static one.kii.kiimate.model.core.ctl.PublishModelCtl.STABILITY;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/publications/{" + STABILITY + "}")
@CrossOrigin(origins = "*")
public class PublishModelCtl extends WriteController {

    public static final String OWNER_ID = "owner-id";

    public static final String STABILITY = "stability";

    @Autowired
    private PublishModelApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<PublishModelApi.Receipt> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(STABILITY) String publication,
            @ModelAttribute PublishModelApi.Form form) {
        return commit(requestId, operatorId, ownerId, publication, form);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PublishModelApi.Receipt> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(STABILITY) String publication,
            @RequestBody PublishModelApi.Form form) {
        return commit(requestId, operatorId, ownerId, publication, form);
    }

    private ResponseEntity<PublishModelApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            String stability,
            PublishModelApi.Form form) {
        form.setStability(stability);
        WriteContext context = buildContext(requestId, operatorId, ownerId);
        return CommitApiCaller.sync(api, context, form);
    }


}
