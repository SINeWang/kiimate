package wang.yanjiong.metamate.core.api;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface CreateExtensionApi {

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Response<Extension> createExtensionViaFormUrlEncoded(@ModelAttribute Request request);

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    Response<Extension> createExtensionViaJson(@RequestBody Request request);


    @Data
    class Request extends wang.yanjiong.metamate.core.model.Form {

        private String group;

        private String name;

        private String version;

        private String visibility;

        private String dataStructure;
    }

    @Data
    class Response<Extension> extends wang.yanjiong.metamate.core.model.Receipt<Extension> {
    }
}
