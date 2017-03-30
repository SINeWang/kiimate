package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.magnet.xi.boundary.Response;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by WangYanJiong on 26/03/2017.
 */

@RestController
@RequestMapping("/v1")
public interface CreateInstanceApi {


    @RequestMapping(value = "/instance/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Receipt createInstanceViaFormUrlEncoded(
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestHeader("X-MM-Owner-Id") String ownerId,
            @RequestHeader("X-MM-Operator-Id") String operatorId,
            HttpServletRequest request);

    @Data
    @EqualsAndHashCode(callSuper=false)
    class Receipt extends Response {

        private List<Instance> instances;
    }

    @Data
    @EqualsAndHashCode(callSuper=false)
    class Instance {

        private String id;

        private String extId;

        private String intId;

        private String ownerId;

        private String operatorId;

        private String field;

        private String[] value;

    }

}
