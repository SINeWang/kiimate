package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.ValueMapping;
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

        AnExtensionExtractor.Extension extension = extensionExtractor.extract(context, commitForm);

        ExtensionDai.Extension daiExtension = ValueMapping.from(ExtensionDai.Extension.class, extension);
        try {
            extensionDai.insertExtension(daiExtension);
            return ValueMapping.from(CommitReceipt.class, daiExtension);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            throw new Conflict(extension.getCommit());
        }
    }

    @Override
    public CancelReceipt cancel(WriteContext context, CancelForm form) throws BadRequest, NotFound {
        return null;
    }


}
