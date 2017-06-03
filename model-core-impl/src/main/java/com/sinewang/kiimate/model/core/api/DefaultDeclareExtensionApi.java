package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.validator.NotNull;
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
    public CommitReceipt commit(WriteContext context, CommitForm form) throws BadRequest, Conflict, NotFound {

        ExtensionDai.Record record = extensionExtractor.extract(context, form);

        NotNull.of(ExtensionDai.Record.class, MayHave.class, record);

        extensionDai.remember(record);

        CommitReceipt receipt = ValueMapping.from(CommitReceipt.class, record);

        return NotNull.of(CommitReceipt.class, receipt);
    }


}
