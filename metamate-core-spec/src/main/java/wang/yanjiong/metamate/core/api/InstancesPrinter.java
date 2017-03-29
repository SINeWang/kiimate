package wang.yanjiong.metamate.core.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by WangYanJiong on 3/29/17.
 */

@Controller
@RequestMapping("/v1/print")
public interface InstancesPrinter {


    @RequestMapping(value = "/properties/instances/{group}/{name}/{tree}", method = RequestMethod.GET)
    CreateExtensionApi.Receipt readInstances(
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestHeader("X-MM-Owner-Id") String ownerId,
            @RequestHeader("X-MM-Operator-Id") String operatorId
    );


}
