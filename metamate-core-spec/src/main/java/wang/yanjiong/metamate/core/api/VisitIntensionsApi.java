package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.ReadContext;
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

    Extension readIntensionsByGroupNameVersion(ReadContext context, Form form);

    @Data
    class Form {
        String group;
        String name;
        String tree;
    }


    @Data
    @EqualsAndHashCode(callSuper = false)
    class Extension {

        private String extId;

        private String ownerId;

        private String group;

        private String name;

        private String tree;

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
