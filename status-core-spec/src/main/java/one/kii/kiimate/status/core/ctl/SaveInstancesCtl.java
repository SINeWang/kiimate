package one.kii.kiimate.status.core.ctl;

import one.kii.kiimate.status.core.api.SaveInstancesApi;
import one.kii.summer.io.context.ErestHeaders;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.receiver.ErestResponse;
import one.kii.summer.io.receiver.WriteController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/api/v1")
public class SaveInstancesCtl extends WriteController {

    @Autowired
    private SaveInstancesApi api;

    @RequestMapping(value = "/{ownerId}/instances/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<SaveInstancesApi.Receipt> commit(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestParam MultiValueMap<String, String> map) {
        try {

            WriteContext context = buildContext(requestId, operatorId, ownerId);

            SaveInstancesApi.Form form = new SaveInstancesApi.Form();
            form.setGroup(group);
            form.setName(name);
            form.setTree(tree);
            form.setMap(map);

            return ErestResponse.created(requestId, api.saveInstance(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        } catch (Conflict conflict) {
            return ErestResponse.conflict(requestId, conflict.getKeys()[0]);
        }
    }

}
