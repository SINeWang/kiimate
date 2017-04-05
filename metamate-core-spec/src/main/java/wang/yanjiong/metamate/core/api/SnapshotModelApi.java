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


    @RequestMapping(value = "/snapshot/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<SnapshotModelReceipt> snapshot(
            @ModelAttribute SnapshotModelForm snapshotModelForm,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader("X-SUMMER-OperatorId") String operatorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree);
    
    @Data
    @EqualsAndHashCode(callSuper = false)
    class SnapshotModelForm {

        private String version;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class SnapshotModelReceipt {

        List<Intension> intensions;

        private String version;

        private Date createdAt;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Intension {

        String field;

        boolean single;
    }

}
