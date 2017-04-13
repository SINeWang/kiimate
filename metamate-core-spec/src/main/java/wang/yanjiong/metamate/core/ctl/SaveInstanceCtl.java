package wang.yanjiong.metamate.core.ctl;

import one.kii.summer.context.exception.NotFound;
import one.kii.summer.context.io.WriteContext;
import one.kii.summer.context.io.WriteController;
import one.kii.summer.erest.ErestHeaders;
import one.kii.summer.erest.ErestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.SaveInstanceApi;

/**
 * Created by WangYanJiong on 4/13/17.
 */

@RestController
@RequestMapping("/v1")
public class SaveInstanceCtl extends WriteController {

    @Autowired
    private SaveInstanceApi api;

    @RequestMapping(value = "/{ownerId}/instance/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<SaveInstanceApi.Receipt> saveInstance(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestParam MultiValueMap<String, String> map) {
        try {

            WriteContext context = buildContext(requestId, operatorId, ownerId);


            SaveInstanceApi.Form form = new SaveInstanceApi.Form();
            form.setGroup(group);
            form.setName(name);
            form.setTree(tree);
            form.setMap(map);

            return ErestResponse.created(requestId, api.saveInstance(context, form));
        } catch (NotFound notFound) {
            return ErestResponse.notFound(requestId, notFound.getKey());
        }
    }

}
