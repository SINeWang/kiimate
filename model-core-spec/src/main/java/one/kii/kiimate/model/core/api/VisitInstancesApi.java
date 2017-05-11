package one.kii.kiimate.model.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by WangYanJiong on 11/5/17.
 */
@RestController
@RequestMapping("/v1")
public interface VisitInstancesApi {


    List<Instance> visit(ReadContext context, Form form) throws NotFound;

    @Data
    class Form {
        String subId;
    }


    @Data
    @EqualsAndHashCode(callSuper = false)
    class Instance {


    }


}
