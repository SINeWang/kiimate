package wang.yanjiong.metamate.core.api;

import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.Response;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface GetIntensionsApi {

    @RequestMapping(value = "/intensions/{extId}", method = RequestMethod.GET)
    Receipt readIntensionsByExiId(@PathVariable("extId") String extId);

    @RequestMapping(value = "/intensions/{group}/{name}/{version}", method = RequestMethod.GET)
    Receipt readIntensionsByGroupNameVersion(@PathVariable("group") String group,
                                      @PathVariable("name") String name,
                                      @PathVariable("version") String version);


    @Data
    class Receipt extends Response {

        private String extId;

        private List<Intension> intensions;
    }

    @Data
    class Intension {

        private String id;

        private String name;

        private boolean single;

        private String structure;

        private String visibility;
    }

}
