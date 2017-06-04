package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
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
    public CommitReceipt commit(WriteContext context, CommitForm form) throws BadRequest, Conflict, NotFound, Panic {

        ExtensionDai.Record record = extensionExtractor.extract(context, form);

        NotBadRequest.from(record);

        extensionDai.remember(record);

        CommitReceipt receipt = ValueMapping.from(CommitReceipt.class, record);

        return NotBadResponse.of(CommitReceipt.class, receipt);
    }


}
