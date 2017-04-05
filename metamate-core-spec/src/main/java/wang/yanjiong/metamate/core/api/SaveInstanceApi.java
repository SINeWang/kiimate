package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by WangYanJiong on 26/03/2017.
 */

@RestController
@RequestMapping("/v1")
public interface SaveInstanceApi {


    @RequestMapping(value = "/instance/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<List<Instance>> saveInstanceViaFormUrlEncoded(
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader("X-SUMMER-OperatorId") String operatorId,
            @RequestHeader("X-SUMMER-RequestId") String requestId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestParam(value = "tag", required = false, defaultValue = "LATEST") String tag,
            HttpServletRequest request);

    @Data
    @EqualsAndHashCode(callSuper = false)
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
