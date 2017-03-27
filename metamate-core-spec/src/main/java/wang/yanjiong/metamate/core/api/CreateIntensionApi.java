package wang.yanjiong.metamate.core.api;

import lombok.Data;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.Request;
import wang.yanjiong.magnet.xi.boundary.Response;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by WangYanJiong on 26/03/2017.
 */
@RestController
@RequestMapping("/v1")
public interface CreateIntensionApi {


    @RequestMapping(value = "/intension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    Receipt createIntensionViaFormUrlEncoded(@ModelAttribute Form form, HttpServletRequest request);


    @Data
    class Form extends Request {

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String visibility;
    }

    @Data
    class Receipt extends Response {

        private String id;

        private String extId;

        private String field;

        private boolean single;

        private String structure;

        private String visibility;

    }

}
