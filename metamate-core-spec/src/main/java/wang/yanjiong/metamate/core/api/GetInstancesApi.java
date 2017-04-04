package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@RestController
@RequestMapping("/v1")
public interface GetInstancesApi {

    @RequestMapping(value = "/instances/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    ResponseEntity<Instance> readInstancesByGroupNameVersion(
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree);

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {
        Map<String, String> instance;
    }

}
