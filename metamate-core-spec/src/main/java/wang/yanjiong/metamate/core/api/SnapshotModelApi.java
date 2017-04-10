package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 4/5/17.
 */

@RestController
@RequestMapping("/v1")
public interface SnapshotModelApi {

    String TREE_MASTER = "master";

    String VISIBILITY_PUBLIC = "public";


    @RequestMapping(value = "/snapshot/{group:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<Receipt> snapshot(
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @RequestHeader("X-MM-OperatorId") String operatorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @ModelAttribute Form form) throws RefereceExtensionHasNotBeenPublished;

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Form {

        private String providerId;

        private String version;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Receipt {

        List<Intension> intensions;
        private String pubSetHash;
        private String providerId;

        private String ownerId;

        private String version;

        private Date createdAt;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Intension {

        String field;

        boolean single;
    }

    class RefereceExtensionHasNotBeenPublished extends Exception {

        public RefereceExtensionHasNotBeenPublished(String message) {
            super(message);
        }
    }

}
