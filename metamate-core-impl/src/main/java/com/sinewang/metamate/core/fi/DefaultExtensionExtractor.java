package com.sinewang.metamate.core.fi;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.DeclareNameApi;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    @Override
    public Extension extract(DeclareNameApi.NameForm nameForm, String ownerId) throws MissingParamException {

        if (nameForm.getGroup() == null || nameForm.getGroup().isEmpty()) {
            throw new MissingParamException("group is NULL or EMPTY");
        }
        if (nameForm.getName() == null || nameForm.getName().isEmpty()) {
            throw new MissingParamException("name is NULL or EMPTY");
        }
        if (nameForm.getTree() == null || nameForm.getTree().isEmpty()) {
            throw new MissingParamException("version is NULL or EMPTY");
        }
        if (nameForm.getStructure() == null || nameForm.getStructure().isEmpty()) {
            throw new MissingParamException("structure is NULL or EMPTY");
        }
        if (nameForm.getVisibility() == null || nameForm.getVisibility().isEmpty()) {
            throw new MissingParamException("visibility is NULL or EMPTY");
        }


        Extension extension = DataTools.copy(nameForm, Extension.class);
        BeanUtils.copyProperties(nameForm, extension);
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
