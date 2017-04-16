package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.fui.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fui.AnVisibilityValidator;

/**
 * Created by WangYanJiong on 3/24/17.
 */
@RestController
@RequestMapping("/v1")
public class DefaultDeclareExtensionApi implements DeclareExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private AnVisibilityValidator visibilityValidator;

    @Override
    public CommitReceipt commit(WriteContext context, CommitForm commitForm) throws BadRequest, Conflict {

        AnExtensionExtractor.Extension extension = extensionExtractor.extract(commitForm);


        boolean isValidVisibility = visibilityValidator.isValid(extension.getVisibility());
        if (!isValidVisibility) {
            throw new BadRequest("visibility");
        }

        ExtensionDai.Extension daiExtension = DataTools.copy(extension, ExtensionDai.Extension.class);

        try {
            extensionDai.insertExtension(daiExtension);
            return DataTools.copy(daiExtension, CommitReceipt.class);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            throw new Conflict(extension.getId());
        }
    }


}
