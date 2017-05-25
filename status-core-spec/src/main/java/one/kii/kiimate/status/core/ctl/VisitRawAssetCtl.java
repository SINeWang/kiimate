package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitRawAssetsApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.util.Map;

import static one.kii.kiimate.status.core.ctl.VisitAssetCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 23/05/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/raw-asset", method = RequestMethod.GET)
@CrossOrigin(origins = "*")
public class VisitRawAssetCtl extends ReadController {

    public static final String OWNER_ID = "owner-id";

    public static final String PUB_SET = "pub-set";

    public static final String VERSION = "version";

    public static final String STABILITY = "stability";

    public static final String GROUP = "group";

    public static final String NAME = "name";

    public static final String FORMAT_YML = "yml";


    @Autowired
    private VisitRawAssetsApi api;

    @RequestMapping(value = "/{" + PUB_SET + "}/{" + STABILITY + "}/{" + VERSION + ":.+}")
    public ResponseEntity<Map<String, Object>> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(PUB_SET) String pubSet,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);
        VisitRawAssetsApi.PubSetForm form = new VisitRawAssetsApi.PubSetForm();
        form.setPubSet(pubSet);
        form.setVersion(version);
        form.setStability(stability);
        try {
            return ErestResponse.ok(requestId, api.visit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + STABILITY + "}/{" + VERSION + ":.+}")
    public ResponseEntity<?> visit(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(STABILITY) String stability,
            @PathVariable(VERSION) String version,
            @RequestParam(value = FORMAT_YML, required = false) String yml) {
        ReadContext context = buildContext(requestId, ownerId, visitorId);

        VisitRawAssetsApi.GroupNameForm form = new VisitRawAssetsApi.GroupNameForm();
        form.setGroup(group);
        form.setName(name);
        if (null != stability) {
            form.setStability(stability);
        }
        if (null != version) {
            form.setVersion(version);
        }
        try {
            if (yml == null) {
                return ErestResponse.ok(requestId, api.visit(context, form));
            } else {
                DumperOptions options = new DumperOptions();
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                Yaml yaml = new Yaml(options);
                return ErestResponse.ok(requestId, yaml.dump(api.visit(context, form)));
            }
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }


}
