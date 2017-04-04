package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.bound.Request;
import one.kii.summer.bound.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
@RestController
@RequestMapping("/v1")
public interface DeclarePropApi {


    @RequestMapping(value = "/intension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<PropReceipt> declarePropViaFormUrlEncoded(@ModelAttribute PropForm propForm,
                                                             @RequestHeader("X-SUMMER-OwnerId") String ownerId,
                                                             HttpServletRequest request);


    @Data
    @EqualsAndHashCode(callSuper = false)
    class PropForm extends Request {

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String visibility;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class PropReceipt extends Response {

        private String id;

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String visibility;

    }

}
