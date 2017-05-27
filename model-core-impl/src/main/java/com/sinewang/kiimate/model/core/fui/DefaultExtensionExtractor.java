package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.validator.Must;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 25/03/2017.
 */

@Component
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    @Override
    public Extension extract(WriteContext context, DeclareExtensionApi.CommitForm commitForm) throws BadRequest {

        Must.have(commitForm);

        commitForm.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getGroup()));
        commitForm.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getName()));
        commitForm.setTree(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getTree()));
        commitForm.setVisibility(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getVisibility()));

        Extension extension = ValueMapping.from(Extension.class, commitForm, context);
        try {
            String visibility = commitForm.getVisibility();
            Visibility.valueOf(visibility.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequest("visibility");
        }
        hashId(extension);
        return extension;
    }

    @Override
    public void hashId(Extension extension) {
        extension.setOwnerId(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, extension.getOwnerId()));
        extension.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, extension.getGroup()));
        extension.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, extension.getName()));
        extension.setTree(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, extension.getTree()));
        extension.setVisibility(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, extension.getVisibility()));
        String id = HashTools.hashHex(extension);
        extension.setId(id);
    }


}
