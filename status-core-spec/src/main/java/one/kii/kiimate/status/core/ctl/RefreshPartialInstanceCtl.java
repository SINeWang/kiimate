package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.RefreshPartialInstanceApi;
import one.kii.summer.asdf.api.CommitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.RefreshEntireInstanceCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 20/6/17.
 */

@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/instance", method = RequestMethod.PUT)
@CrossOrigin(origins = "*")
public class RefreshPartialInstanceCtl extends WriteController {

    static final String OWNER_ID = "owner-id";

    static final String SUB_ID = "sub-id";

    static final String FIELD = "field";

    @Autowired
    private RefreshPartialInstanceApi api;


    @RequestMapping(value = "/{" + SUB_ID + "}/fields/{" + FIELD + "}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) String subId,
            @PathVariable(FIELD) String field,
            @RequestBody RefreshPartialInstanceApi.Values values) {
        WriteContext context = buildContext(requestId, ownerId, operatorId);
        RefreshPartialInstanceApi.SubIdForm form = new RefreshPartialInstanceApi.SubIdForm();
        form.setId(subId);
        form.setField(field);
        form.setValues(values);
        return CommitApiCaller.sync(api, context, form);
    }


}
