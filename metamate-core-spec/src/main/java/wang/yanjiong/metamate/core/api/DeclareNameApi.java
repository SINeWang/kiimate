package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.bound.Request;
import one.kii.summer.bound.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface DeclareNameApi {

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<NameReceipt> declareByFormUrlEncoded(@ModelAttribute NameForm nameForm,
                                                        @RequestHeader("X-MM-Owner-Id") String ownerId,
                                                        @RequestHeader("X-MM-Operator-Id") String operatorId);

    @Data
    @EqualsAndHashCode(callSuper = false)
    class NameForm extends Request {

        private String group;

        private String name;

        private String tree;

        private String visibility;

        private String structure;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class NameReceipt extends Response {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String visibility;

        private String structure;

    }

}
