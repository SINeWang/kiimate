package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@RestController
@RequestMapping("/v1")
public interface GetInstancesApi {

    @RequestMapping(value = "/instances/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    ResponseEntity<List<Instance>> readInstancesByGroupNameVersion(@PathVariable("group") String group,
                                                                   @PathVariable("name") String name,
                                                                   @PathVariable("tree") String tree);

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {
        Map<String, String> instance;
    }

}
