package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.bound.Request;
import one.kii.summer.bound.Response;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface SetExtensionApi {

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Receipt declareExtensionViaFormUrlEncoded(@ModelAttribute Form form,
                                              @RequestHeader("X-MM-Owner-Id") String ownerId,
                                              @RequestHeader("X-MM-Operator-Id") String operatorId);

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form extends Request {

        private String group;

        private String name;

        private String tree;

        private String visibility;

        private String structure;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt extends Response {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String visibility;

        private String structure;

    }

}
