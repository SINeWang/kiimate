package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 3/24/17.
 */

@Component
public class DefaultDeclareExtensionApi implements DeclareExtensionApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Override
    public CommitReceipt commit(WriteContext context, CommitForm commitForm) throws BadRequest, Conflict {

        AnExtensionExtractor.Extension extension = extensionExtractor.extract(commitForm);

        ExtensionDai.Extension daiExtension = DataTools.copy(extension, ExtensionDai.Extension.class);
        daiExtension.setOwnerId(context.getOwnerId());

        try {
            extensionDai.insertExtension(daiExtension);
            return DataTools.copy(daiExtension, CommitReceipt.class);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            throw new Conflict(extension.getId());
        }
    }

    @Override
    public CancelReceipt cancel(WriteContext context, CancelForm form) throws BadRequest, NotFound {
        return null;
    }


}
