package wang.yanjiong.metamate.core.api;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
@RestController
@RequestMapping("/v1")
public interface DeclareIntensionApi {


    @RequestMapping(value = "{ownerId}/intension/{group}/{name}/{tree:.+}", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<IntensionReceipt> declarePropViaFormUrlEncoded1(
            @ModelAttribute IntensionForm1 intensionForm,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-OperatorId", required = false) String operatorId);

    @RequestMapping(value = "{ownerId}/intension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    ResponseEntity<IntensionReceipt> declarePropViaFormUrlEncoded2(
            @ModelAttribute IntensionForm2 intensionForm,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-OperatorId", required = false) String operatorId);


    @Data
    @EqualsAndHashCode(callSuper = false)
    class IntensionForm1 {

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class IntensionForm2 extends IntensionForm1 {

        private String extId;

    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    class IntensionReceipt {

        private String id;

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

    }

}
