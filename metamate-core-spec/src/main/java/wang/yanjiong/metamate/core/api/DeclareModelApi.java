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
public interface DeclareModelApi {


    @RequestMapping(value = "/model/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<DeclareExtensionApi.ExtensionReceipt> declareByFormUrlEncoded(
            @ModelAttribute DeclareExtensionApi.ExtensionForm extensionForm,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader("X-SUMMER-OperatorId") String operatorId,
            @RequestParam(value = "fields", defaultValue = "ALL", required = false) String fields);


    @Data
    @EqualsAndHashCode(callSuper = false)
    class ModelTagForm {

        private String name;

        private String visibility;

        private String[] fields;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class ModelTagReceipt {

        List<Intension> intensions;
        private String id;
        private String name;
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
