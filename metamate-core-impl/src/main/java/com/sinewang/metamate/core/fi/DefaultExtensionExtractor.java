package com.sinewang.metamate.core.fi;

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
        if (extensionForm.getStructure() == null || extensionForm.getStructure().isEmpty()) {
            throw new MissingParamException("structure is NULL or EMPTY");
        }
        if (extensionForm.getVisibility() == null || extensionForm.getVisibility().isEmpty()) {
            throw new MissingParamException("visibility is NULL or EMPTY");
        }


        Extension extension = DataTools.copy(extensionForm, Extension.class);
        BeanUtils.copyProperties(extensionForm, extension);
        extension.setOwnerId(ownerId);
        String id = hashExtension(extension);
        extension.setId(id);
        return extension;
    }

    @Override
    public String hashId(String ownerId, String group, String name, String tree) {
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
