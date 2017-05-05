package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.validator.Must;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 25/03/2017.
 */

@Component
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    @Override
    public Extension extract(DeclareExtensionApi.CommitForm commitForm) throws BadRequest {


        Must.have(commitForm);

        commitForm.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getGroup()));
        commitForm.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getName()));
        commitForm.setTree(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getTree()));
        commitForm.setVisibility(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, commitForm.getVisibility()));


        Extension extension = DataTools.copy(commitForm, Extension.class);
        try {
            String visibility = commitForm.getVisibility();
            Visibility.valueOf(visibility.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequest("visibility");
        }
        String id = hashExtension(extension);
        extension.setId(id);
        return extension;
    }

    @Override
    public String hashId(String ownerId, String group, String name, String tree, String visibility) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);
        tree = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tree);

        return HashTools.hashHex(ownerId, group, name, tree, visibility);
    }

    private String hashExtension(Extension extension) {
        return hashId(extension.getOwnerId(), extension.getGroup(), extension.getName(), extension.getTree(), extension.getVisibility());
    }


}
