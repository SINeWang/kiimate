package wang.yanjiong.metamate.core.api;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.model.Request;
import wang.yanjiong.metamate.core.model.Response;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface CreateExtensionApi {

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Receipt createExtensionViaFormUrlEncoded(@ModelAttribute Form form);

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Receipt createExtensionViaJson(@RequestBody Form form);

    @Data
    class Form extends Request {

        private String group;

        private String name;

        private String version;

        private String visibility;

        private String dataStructure;
    }

    @Data
    class Receipt<Extension> extends Response<Extension> {
    }

    @Data
    class Extension {

        private String id;

        private String group;

        private String name;

        private String version;

        private String visibility;

        private String dataStructure;
    }
}
