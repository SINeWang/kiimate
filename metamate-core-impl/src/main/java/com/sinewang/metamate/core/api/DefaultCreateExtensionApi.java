package com.sinewang.metamate.core.api;

import com.sinewang.metamate.core.util.DataUtil;
import com.sinewang.metamate.core.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.fi.ExtensionFi;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
public class DefaultCreateExtensionApi implements CreateExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private ExtensionFi extensionFi;

    @Override
    public Receipt createExtensionViaFormUrlEncoded(Form form) {

        ExtensionFi.Extension extension = extensionFi.accept(form);

        ExtensionDai.Extension daiRecord = DataUtil.clone(extension, ExtensionDai.Extension.class);

        extensionDai.insertExtension(daiRecord);

        return ResponseUtil.build(form, Receipt.class, extension);
    }

    @Override
    public Receipt createExtensionViaJson(Form form) {
        return null;
    }
}
