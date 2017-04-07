package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface DeclareExtensionApi {

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<ExtensionReceipt> declareByFormUrlEncoded(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @ModelAttribute ExtensionForm extensionForm,
            @RequestHeader("X-MM-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId);

    @Data
    @EqualsAndHashCode(callSuper = false)
    class ExtensionForm {

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class ExtensionReceipt {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }

}
