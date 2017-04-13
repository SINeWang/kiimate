package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface VisitIntensionsApi {

    String VISIBILITY_PUBLIC = "public";

    ResponseEntity<Extension> readIntensionsByGroupNameVersion(
            String requestId,
            String visitorId,
            String ownerId,
            String group,
            String name,
            String tree);


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
