package wang.yanjiong.metamate.core.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import wang.yanjiong.metamate.core.api.form.CreateExtensionForm;
import wang.yanjiong.metamate.core.api.receipt.CreateExtensionReceipt;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@RequestMapping("/v1")
public interface CreateExtensionApi {

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    CreateExtensionReceipt createExtensionViaFormUrlEncoded(@ModelAttribute CreateExtensionForm extensionForm);

    @RequestMapping(value = "/extension", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    CreateExtensionReceipt createExtensionViaJson(@ModelAttribute CreateExtensionForm extensionForm);

}
