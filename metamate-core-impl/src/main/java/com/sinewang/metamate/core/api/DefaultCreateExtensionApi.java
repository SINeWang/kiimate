package com.sinewang.metamate.core.api;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.util.ResponseUtil;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionFormParser;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;
import wang.yanjiong.metamate.core.fi.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
public class DefaultCreateExtensionApi implements CreateExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private AnExtensionFormParser extensionFormParser;

    @Autowired
    private AnStructureValidator structureValidator;

    @Autowired
    private AnVisibilityValidator visibilityValidator;

    @Override
    public Receipt createExtensionViaFormUrlEncoded(Form form) {

        AnExtensionFormParser.Extension extension = extensionFormParser.parse(form);

        boolean isValidStructure = structureValidator.isValid(extension.getStructure());

        boolean isValidVisibility = visibilityValidator.isValid(extension.getVisibility());

        ExtensionDai.Extension daiRecord = new ExtensionDai.Extension();
        BeanUtils.copyProperties(extension, daiRecord);

        try {
            extensionDai.insertExtension(daiRecord);
            return ResponseUtil.accepted(form, Receipt.class, extension);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            return ResponseUtil.rejected(form, Receipt.class, extension);
        }


    }

    @Override
    public Receipt createExtensionViaJson(Form form) {
        return null;
    }
}
