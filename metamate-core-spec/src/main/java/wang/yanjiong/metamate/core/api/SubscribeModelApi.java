package wang.yanjiong.metamate.core.api;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RestController
@RequestMapping("/v1")
public interface SubscribeModelApi {

    @RequestMapping(value = "/subscribe/{providerId}/{extId}/{publication}/{version}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Receipt> subscribe(
            @ModelAttribute Form form,
            @RequestHeader("X-SUMMER-SubscriberId") String subscriberId,
            @RequestHeader("X-SUMMER-OperatorId") String operatorId,
            @PathVariable("providerId") String providerId,
            @PathVariable("extId") String extId,
            @PathVariable("publication") String publication,
            @PathVariable("version") String version);

    @Data
    class Form {
        private String group;
        private String name;
        private String tree;
    }


    @Data
    class Receipt {

        private String id;

        private String providerId;

        private String extId;

        private String publication;

        private String version;

        private String subscriberId;

        private String group;

        private String name;

        private String tree;

        private String operatorId;
    }
}
