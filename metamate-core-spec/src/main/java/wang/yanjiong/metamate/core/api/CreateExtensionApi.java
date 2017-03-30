package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.magnet.xi.boundary.Request;
import wang.yanjiong.magnet.xi.boundary.Response;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface CreateExtensionApi {

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Receipt createExtensionViaFormUrlEncoded(@ModelAttribute Form form);

//    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
//    Receipt createExtensionViaJson(@RequestBody Form form);


    @Data
    @EqualsAndHashCode(callSuper=false)
    class Form extends Request {

        private String group;

        private String name;

        private String tree;

        private String visibility;

        private String structure;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    class Receipt extends Response {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String visibility;

        private String structure;

    }

}
