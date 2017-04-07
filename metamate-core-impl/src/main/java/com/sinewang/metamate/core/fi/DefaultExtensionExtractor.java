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
    public Extension extract(DeclareExtensionApi.ExtensionForm extensionForm, String ownerId) throws MissingParamException {

        if (extensionForm.getGroup() == null || extensionForm.getGroup().isEmpty()) {
            throw new MissingParamException("group is NULL or EMPTY");
        }
        if (extensionForm.getName() == null || extensionForm.getName().isEmpty()) {
            throw new MissingParamException("name is NULL or EMPTY");
        }
        if (extensionForm.getTree() == null || extensionForm.getTree().isEmpty()) {
            throw new MissingParamException("version is NULL or EMPTY");
        }
        if (extensionForm.getVisibility() == null || extensionForm.getVisibility().isEmpty()) {
            throw new MissingParamException("visibility is NULL or EMPTY");
        }
        extensionForm.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, extensionForm.getGroup()));
        extensionForm.setName(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, extensionForm.getName()));
        extensionForm.setTree(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, extensionForm.getTree()));

        Extension extension = DataTools.copy(extensionForm, Extension.class);
        extension.setOwnerId(ownerId);
        BeanUtils.copyProperties(extensionForm, extension);
        String id = hashExtension(extension);
        extension.setId(id);
        return extension;
    }

    @Override
    public String hashId(String ownerId, String group, String name, String tree) {
        group = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, group);
        name = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, name);
        tree = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, tree);

        return HashTools.hashHex(
                ownerId,
                group, name, tree
        );
    }

    private String hashExtension(Extension extension) {
        return hashId(
                extension.getOwnerId(), extension.getGroup(), extension.getName(), extension.getTree()
        );
    }


}
