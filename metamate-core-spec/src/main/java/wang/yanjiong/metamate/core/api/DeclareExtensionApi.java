package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.erest.ErestHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface DeclareExtensionApi {

    @RequestMapping(value = "/{ownerId}/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Receipt> declareByFormUrlEncoded(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @ModelAttribute Form form);

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }

}
