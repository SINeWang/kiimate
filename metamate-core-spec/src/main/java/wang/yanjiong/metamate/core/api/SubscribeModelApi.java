package wang.yanjiong.metamate.core.api;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RestController
@RequestMapping("/v1")
public interface SubscribeModelApi {

    @RequestMapping(value = "/subscribe/{pubSetHash}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Receipt> subscribe(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @RequestHeader("X-MM-SubscriberId") String subscriberId,
            @RequestHeader("X-MM-OperatorId") String operatorId,
            @PathVariable("pubSetHash") String pubSetHash,
            @ModelAttribute Form form);

    @Data
    class Form {
        private String group;
    }


    @Data
    class Receipt {

        String pubSetHash;

        List<ModelSubscription> subscriptions;
    }

    @Data
    class ModelSubscription {
        private String id;
        private String pubExtId;
        private String subscriberId;
        private String group;
        private String name;
        private String tree;
        private String operatorId;
    }
}
