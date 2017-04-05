package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface VisitIntensionsApi {

    @RequestMapping(value = "/intensions/{group}/{name}/{tree.+}", method = RequestMethod.GET)
    ResponseEntity<Extension> readIntensionsByGroupNameVersion(
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree);


    @Data
    @EqualsAndHashCode(callSuper = false)
    class Extension {

        private String extId;

        private List<Intension> intensions;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Intension {

        private String id;

        private String field;

        private boolean single;

        private String structure;

        private String visibility;
    }

}
