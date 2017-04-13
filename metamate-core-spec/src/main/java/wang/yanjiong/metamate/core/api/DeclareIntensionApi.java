package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.erest.ErestHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
@RestController
@RequestMapping("/v1")
public interface DeclareIntensionApi {


    @RequestMapping(value = "/{ownerId}/intension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Receipt> declarePropViaFormUrlEncoded2(
            @RequestHeader(ErestHeaders.REQUEST_ID) String requestId,
            @RequestHeader(ErestHeaders.OPERATOR_ID) String operatorId,
            @PathVariable("ownerId") String ownerId,
            @ModelAttribute Form form);

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
