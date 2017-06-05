package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.PublishStatusApi;
import one.kii.summer.asdf.xi.CommitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.PublishStatusCtl.OWNER_ID;
import static one.kii.kiimate.status.core.ctl.PublishStatusCtl.STATUS_ID;


/**
 * Created by WangYanJiong on 19/05/2017.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/publications/status/{" + STATUS_ID + "}")
@CrossOrigin(origins = "*")
public class PublishStatusCtl extends WriteController {

    public static final String OWNER_ID = "owner-id";

    public static final String STATUS_ID = "status_id";


    @Autowired
    private PublishStatusApi api;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<PublishStatusApi.Receipt> commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(STATUS_ID) Long statusId,
            @ModelAttribute PublishStatusApi.Form form) {
        return commit(requestId, operatorId, ownerId, statusId, form);
    }


    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PublishStatusApi.Receipt> commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(STATUS_ID) Long statusId,
            @RequestBody PublishStatusApi.Form form) {
        return commit(requestId, operatorId, ownerId, statusId, form);
    }

    private ResponseEntity<PublishStatusApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            Long statusId,
            PublishStatusApi.Form form) {
        form.setId(statusId);
        form.setProviderId(ownerId);
        WriteContext context = buildContext(requestId, operatorId, ownerId);
        return CommitApiCaller.sync(api, context, form);
    }
}
