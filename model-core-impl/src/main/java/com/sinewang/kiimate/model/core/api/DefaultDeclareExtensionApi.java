package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
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

        ExtensionDai.Record record = ValueMapping.from(ExtensionDai.Record.class, extension);
        try {
            extensionDai.remember(record);
            return ValueMapping.from(CommitReceipt.class, record);
        } catch (ExtensionDai.ExtensionDuplicated extensionDuplicated) {
            throw new Conflict(extension.getCommit());
        }
    }


}
