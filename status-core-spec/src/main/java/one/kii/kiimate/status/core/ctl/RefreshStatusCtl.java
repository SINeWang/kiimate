package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.RefreshStatusApi;
import one.kii.summer.asdf.api.CommitApiCaller;
import one.kii.summer.beans.utils.MultiValueMapTools;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.RefreshStatusCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/status", method = RequestMethod.PUT)
@CrossOrigin(origins = "*")
public class RefreshStatusCtl extends WriteController {

    static final String OWNER_ID = "owner-id";

    static final String SUB_ID = "sub-id";


    @Autowired
    private RefreshStatusApi api;

    @RequestMapping(value = "/{" + SUB_ID + "}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) String subId,
            @RequestParam MultiValueMap<String, String> map) {
        return commit(requestId, operatorId, ownerId, subId, map);
    }


    @RequestMapping(value = "/{" + SUB_ID + "}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) String subId,
            @RequestBody String json) {
        MultiValueMap<String, String> mmap = MultiValueMapTools.from(json);
        return commit(requestId, operatorId, ownerId, subId, mmap);
    }


    private ResponseEntity<RefreshStatusApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            String subId,
            MultiValueMap<String, String> map) {
        WriteContext context = buildContext(requestId, ownerId, operatorId);
        RefreshStatusApi.SubIdForm form = new RefreshStatusApi.SubIdForm();
        form.setId(subId);
        form.setMap(map);
        return CommitApiCaller.sync(api, context, form);
    }


}
