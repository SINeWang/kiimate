package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.erest.ErestHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface VisitIntensionsApi {

    String VISIBILITY_PUBLIC = "public";

    @RequestMapping(value = "/{ownerId}/intension/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    ResponseEntity<Extension> readIntensionsByGroupNameVersion(
            @RequestHeader(value = ErestHeaders.REQUEST_ID, required = false) String requestId,
            @RequestHeader(ErestHeaders.VISITOR_ID) String visitorId,
            @PathVariable("ownerId") String ownerId,
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

        private String refExtId;

        private String visibility;
    }

}
