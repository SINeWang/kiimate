package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.asdf.api.VisitApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface VisitIntensionsApi extends VisitApi<VisitIntensionsApi.Receipt, ReadContext, VisitIntensionsApi.Form> {

    String VISIBILITY_PUBLIC = "public";

    Receipt visit(ReadContext context, Form form) throws BadRequest, NotFound, Panic;

    @Data
    class Form {
        String group;
        String name;
        String tree;
        String visibility = VISIBILITY_PUBLIC;
    }


    @Data
    class Receipt {

        private String extId;

        private String ownerId;

        private String group;

        private String name;

        private String tree;

        private List<Intension> intensions;
    }

    @Data
    class Intension {

        private String id;

        private String field;

        private Boolean single;

        private String structure;

        private String refPubSet;

        private String visibility;

        private Boolean required;
    }

}
