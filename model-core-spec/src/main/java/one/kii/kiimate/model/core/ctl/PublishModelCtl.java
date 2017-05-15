package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.PublishModelApi;
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

import static one.kii.kiimate.model.core.ctl.PublishModelCtl.OWNER_ID;
import static one.kii.kiimate.model.core.ctl.PublishModelCtl.PUBLICATION;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/publications/{" + PUBLICATION + "}")
@CrossOrigin(origins = "*")
public class PublishModelCtl extends WriteController {

    public static final String OWNER_ID = "ownerId";

    public static final String PUBLICATION = "publication";

    @Autowired
    private PublishModelApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<PublishModelApi.Receipt> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(PUBLICATION) String publication,
            @ModelAttribute PublishModelApi.Form form) {
        return commit(requestId, operatorId, ownerId, publication, form);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PublishModelApi.Receipt> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(PUBLICATION) String publication,
            @RequestBody PublishModelApi.Form form) {
        return commit(requestId, operatorId, ownerId, publication, form);
    }

    private ResponseEntity<PublishModelApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            String publication,
            PublishModelApi.Form form) {
        form.setPublication(publication);
        try {
            WriteContext context = buildContext(requestId, operatorId, ownerId);
            return ErestResponse.created(requestId, api.commit(context, form));
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getFields());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys());
        }
    }


}
