package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by WangYanJiong on 26/03/2017.
 */

@RestController
@RequestMapping("/v1")
public interface SaveInstanceApi {

    String NAME_ROOT = "root";

    String TREE_MASTER = "master";

    @RequestMapping(value = "/{ownerId}/instance/{group}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Receipt> saveInstance(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @RequestHeader("X-MM-OperatorId") String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("tree") String tree,
            @RequestParam MultiValueMap<String, String> map);

    @RequestMapping(value = "/{ownerId}/instance/{group}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Receipt> saveInstance(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @RequestHeader("X-MM-OperatorId") String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @RequestParam MultiValueMap<String, String> map);

    @Data
    class Receipt {
        String group;

        String name;

        String tree;

        List<Instance> instances;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {

        private String id;

        private String extId;

        private String intId;

        private String ownerId;

        private String operatorId;

        private String field;

        private String[] value;

    }

}
