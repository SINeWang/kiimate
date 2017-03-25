package com.sinewang.metamate.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.api.CreateExtensionForm;
import wang.yanjiong.metamate.core.api.CreateExtensionReceipt;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.util.MessageDigestUtil;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
public class DefaultCreateExtensionApi implements CreateExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Override
    public CreateExtensionReceipt createExtensionViaFormUrlEncoded(CreateExtensionForm extensionForm) {
        CreateExtensionReceipt receipt = new CreateExtensionReceipt();
        String id = MessageDigestUtil.hashHex(extensionForm.getGroup());
        receipt.setId(id);
        return receipt;
    }

    @Override
    public CreateExtensionReceipt createExtensionViaJson(@RequestBody CreateExtensionForm extensionForm) {
        CreateExtensionReceipt receipt = new CreateExtensionReceipt();
        receipt.setId(extensionForm.getGroup());
        return receipt;
    }
}
