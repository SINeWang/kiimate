package com.sinewang.metamate.core.fi;

import one.kii.summer.bound.Context;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.DeclareNameApi;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.UUID;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    @Override
    public Extension extract(DeclareNameApi.NameForm nameForm) throws MissingParamException {
        Context context = new Context();
        context.setProcessId(UUID.randomUUID().toString());
        nameForm.setContext(context);


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

        Extension extension = new Extension();
        BeanUtils.copyProperties(nameForm, extension);
        String id = hashExtension(extension);
        extension.setId(id);


        return extension;
    }

    @Override
    public String hashId(String group, String name, String version) {
        return HashTools.hashHex(
                group, name, version
        );
    }

    private String hashExtension(Extension extension) {
        return hashId(
                extension.getGroup(),
                extension.getName(),
                extension.getTree()
        );
    }


}
