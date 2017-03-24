package wang.yanjiong.metamate.core.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RestController
@RequestMapping("/v1")
public interface CreateExtensionApi {

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    CreateExtensionReceipt createExtensionViaFormUrlEncoded(@ModelAttribute CreateExtensionForm extensionForm);

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CreateExtensionReceipt createExtensionViaJson(@RequestBody CreateExtensionForm extensionForm);

}
