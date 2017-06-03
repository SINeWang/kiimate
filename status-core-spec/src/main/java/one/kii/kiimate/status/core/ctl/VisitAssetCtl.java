package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitFatAssetApi;
import one.kii.summer.asdf.xi.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static one.kii.kiimate.status.core.ctl.VisitAssetCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 21/05/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/asset", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitAssetCtl extends ReadController {

    public static final String OWNER_ID = "owner-id";

    public static final String VERSION = "version";

    public static final String STABILITY = "stability";

    public static final String GROUP = "group";

    public static final String NAME = "name";

    @Autowired
    private VisitFatAssetApi api;


    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + STABILITY + "}/{" + VERSION + ":.+}")
    public ResponseEntity<VisitFatAssetApi.Asset> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitFatAssetApi.GroupNameForm form = new VisitFatAssetApi.GroupNameForm();
        form.setGroup(group);
        form.setName(name);
        if (null != stability) {
            form.setStability(stability);
        }
        if (null != version) {
            form.setVersion(version);
        }
        return VisitApiCaller.sync(api, context, form);
    }

}
