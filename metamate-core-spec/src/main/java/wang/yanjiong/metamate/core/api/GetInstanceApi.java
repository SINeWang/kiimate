package wang.yanjiong.metamate.core.api;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@RestController
@RequestMapping("/v1")
public interface GetInstanceApi {

    @RequestMapping(value = "/instance/{group}/{name}/{version}", method = RequestMethod.GET)
    Receipt readInstanceByGroupNameVersionWithOwner(@PathVariable("group") String group,
                                                    @PathVariable("name") String name,
                                                    @PathVariable("version") String version,
                                                    @RequestHeader("X-MM-Owner-Id") String ownerId,
                                                    @RequestHeader("X-MM-Operator-Id") String operatorId);

    @Data
    class Receipt {

        private List<Instance> instances;
    }

    @Data
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
