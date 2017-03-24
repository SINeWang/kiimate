package com.sinewang.metamate.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.api.form.CreateExtensionForm;
import wang.yanjiong.metamate.core.api.receipt.CreateExtensionReceipt;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
public class DefaultCreateExtensionApi implements CreateExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;


    @Override
    public CreateExtensionReceipt createExtensionViaFormUrlEncoded(CreateExtensionForm extensionForm) {
        return null;
    }

    @Override
    public CreateExtensionReceipt createExtensionViaJson(CreateExtensionForm extensionForm) {
        return null;
    }
}
