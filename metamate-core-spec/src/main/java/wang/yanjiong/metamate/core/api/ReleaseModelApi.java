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
public interface ReleaseModelApi {


    @RequestMapping(value = "/release/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<DeclareExtensionApi.ExtensionReceipt> declareByFormUrlEncoded(
            @ModelAttribute DeclareExtensionApi.ExtensionForm extensionForm,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader("X-SUMMER-OperatorId") String operatorId);


    @Data
    @EqualsAndHashCode(callSuper = false)
    class SnapshotModelForm {

        private String version;

        private String visibility;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class SnapshotModelReceipt {

        List<Intension> intensions;

        private String version;

        private String visibility;

        private Date createdAt;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class Intension {

        String field;

        boolean single;
    }

}
