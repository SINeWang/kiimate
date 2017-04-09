package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
@RestController
@RequestMapping("/v1")
public interface DeclareIntensionApi {


    @RequestMapping(value = "/intension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Receipt> declarePropViaFormUrlEncoded2(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @ModelAttribute Form form,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-OperatorId", required = false) String operatorId);

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;


    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        private String id;

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

    }

}
