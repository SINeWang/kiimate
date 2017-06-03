package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.RefreshStatusApi;
import one.kii.summer.beans.utils.MultiValueMapTools;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
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

    static final String GROUP = "group";

    static final String NAME = "name";

    static final String TREE = "tree";

    @Autowired
    private RefreshStatusApi api;

    @RequestMapping(value = "/{" + SUB_ID + "}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) Long subId,
            @RequestParam MultiValueMap<String, String> map) {
        return commit(requestId, operatorId, ownerId, subId, map);
    }

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + TREE + "}", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity commitForm(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(TREE) String tree,
            @RequestParam MultiValueMap<String, String> map) {
        return commit(requestId, operatorId, ownerId, group, name, tree, map);
    }


    @RequestMapping(value = "/{" + SUB_ID + "}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(SUB_ID) Long subId,
            @RequestBody String json) {
        MultiValueMap<String, String> mmap = MultiValueMapTools.from(json);
        return commit(requestId, operatorId, ownerId, subId, mmap);
    }

    @RequestMapping(value = "/{" + GROUP + "}/{" + NAME + "}/{" + TREE + "}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity commitJson(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable(OWNER_ID) String ownerId,
            @PathVariable(GROUP) String group,
            @PathVariable(NAME) String name,
            @PathVariable(TREE) String tree,
            @RequestParam MultiValueMap<String, String> map) {
        return commit(requestId, operatorId, ownerId, group, name, tree, map);
    }

    private ResponseEntity<RefreshStatusApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            long subId,
            MultiValueMap<String, String> map) {
        try {
            WriteContext context = buildContext(requestId, ownerId, operatorId);
            RefreshStatusApi.SubIdForm form = new RefreshStatusApi.SubIdForm();
            form.setSubId(subId);
            form.setMap(map);
            return ErestResponse.created(requestId, api.commit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys());
        }
    }

    private ResponseEntity<RefreshStatusApi.Receipt> commit(
            String requestId,
            String operatorId,
            String ownerId,
            String group,
            String name,
            String tree,
            MultiValueMap<String, String> map) {
        try {
            WriteContext context = buildContext(requestId, ownerId, operatorId);
            RefreshStatusApi.GroupNameTreeForm form = new RefreshStatusApi.GroupNameTreeForm();
            form.setGroup(group);
            form.setName(name);
            form.setTree(tree);
            form.setMap(map);
            return ErestResponse.created(requestId, api.commit(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys());
        } catch (BadRequest badRequest) {
            return ErestResponse.badRequest(requestId, badRequest.getFields());
        }
    }


}
