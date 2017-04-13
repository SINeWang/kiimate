package com.sinewang.metamate.core.fi;

import com.google.common.base.CaseFormat;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import one.kii.summer.context.exception.BadRequest;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    @Override
    public Extension extract(DeclareExtensionApi.Form form) throws BadRequest {

        if (form.getGroup() == null || form.getGroup().isEmpty()) {
            throw new BadRequest("group");
        }
        if (form.getName() == null || form.getName().isEmpty()) {
            throw new BadRequest("name");
        }
        if (form.getTree() == null || form.getTree().isEmpty()) {
            throw new BadRequest("tree");
        }
        if (form.getVisibility() == null || form.getVisibility().isEmpty()) {
            throw new BadRequest("visibility");
        }
        if (form.getOwnerId() == null || form.getOwnerId().isEmpty()) {
            throw new BadRequest("ownerId");
        }
        if (form.getOperatorId() == null || form.getOperatorId().isEmpty()) {
            throw new BadRequest("operatorId");
        }
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));
        form.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getName()));
        form.setTree(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getTree()));

        Extension extension = DataTools.copy(form, Extension.class);
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
