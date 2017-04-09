package com.sinewang.metamate.core.fi;

import com.google.common.base.CaseFormat;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    @Override
    public Extension extract(DeclareExtensionApi.Form form, String ownerId) throws MissingParamException {

        if (form.getGroup() == null || form.getGroup().isEmpty()) {
            throw new MissingParamException("group is NULL or EMPTY");
        }
        if (form.getName() == null || form.getName().isEmpty()) {
            throw new MissingParamException("name is NULL or EMPTY");
        }
        if (form.getTree() == null || form.getTree().isEmpty()) {
            throw new MissingParamException("version is NULL or EMPTY");
        }
        if (form.getVisibility() == null || form.getVisibility().isEmpty()) {
            throw new MissingParamException("visibility is NULL or EMPTY");
        }
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));
        form.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getName()));
        form.setTree(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getTree()));

        Extension extension = DataTools.copy(form, Extension.class);
        extension.setOwnerId(ownerId);
        BeanUtils.copyProperties(form, extension);
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
