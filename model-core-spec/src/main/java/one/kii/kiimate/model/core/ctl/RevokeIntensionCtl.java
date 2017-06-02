package one.kii.kiimate.model.core.ctl;

import one.kii.kiimate.model.core.api.RemoveIntensionApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.model.core.ctl.DeclareIntensionCtl.OWNER_ID;
import static one.kii.kiimate.model.core.ctl.RevokeIntensionCtl.EXT_ID;
import static one.kii.kiimate.model.core.ctl.RevokeIntensionCtl.INT_ID;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1/{" + OWNER_ID + "}/extensions/{" + EXT_ID + "}/intensions/{" + INT_ID + "}")
@CrossOrigin(value = "*")
public class RevokeIntensionCtl extends WriteController {

    public static final String OWNER_ID = "owner-id";

    public static final String INT_ID = "int-id";

    public static final String EXT_ID = "ext-id";

    @Autowired
    private RemoveIntensionApi api;


    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<RemoveIntensionApi.Receipt> commit(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(EXT_ID) Long extId,
            @PathVariable(INT_ID) Long intId) {
        try {
            WriteContext context = buildContext(requestId, operatorId, ownerId);

            RemoveIntensionApi.Form form = new RemoveIntensionApi.Form();
            form.setId(intId);
            form.setExtId(extId);
            form.setOwnerId(ownerId);

            RemoveIntensionApi.Receipt receipt = api.commit(context, form);

            return ErestResponse.created(requestId, receipt);
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys());
        }
    }

}
