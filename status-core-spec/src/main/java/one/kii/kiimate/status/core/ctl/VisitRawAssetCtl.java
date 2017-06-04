package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.VisitRawAssetApi;
import one.kii.summer.asdf.xi.VisitApiCaller;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.ReadController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import static one.kii.kiimate.status.core.ctl.VisitAssetCtl.OWNER_ID;


/**
 * Created by WangYanJiong on 23/05/2017.
 */
@RestController
@RequestMapping(value = "/api/v1/{" + OWNER_ID + "}/asset", method = RequestMethod.GET)
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
    private VisitRawAssetApi api;

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + STABILITY + "}/{" + VERSION + ":.+}/raw")
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

        VisitRawAssetApi.GroupNameForm form = new VisitRawAssetApi.GroupNameForm();
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
                return VisitApiCaller.sync(api, context, form);
            } else {
                DumperOptions options = new DumperOptions();
                options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
                try {
                    Yaml yaml = new Yaml(options);
                    return ErestResponse.ok(requestId, yaml.dump(api.visit(context, form)));
                } catch (BadRequest badRequest) {
                    return ErestResponse.badRequest(requestId, badRequest.getKeys());
                } catch (Panic panic) {
                    return ErestResponse.badRequest(requestId, panic.getKeys());
                }
            }
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKeys());
        }
    }


}
