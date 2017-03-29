package com.sinewang.metamate.core.fi;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.magnet.util.HashUtil;
import wang.yanjiong.magnet.xi.boundary.Context;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.UUID;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionExtractor implements AnExtensionExtractor {

    @Override
    public Extension extract(CreateExtensionApi.Form form) throws MissingParamException {
        Context context = new Context();
        context.setProcessId(UUID.randomUUID().toString());
        form.setContext(context);


        if (form.getGroup() == null || form.getGroup().isEmpty()) {
            throw new MissingParamException("group is NULL or EMPTY");
        }
        if (form.getName() == null || form.getName().isEmpty()) {
            throw new MissingParamException("name is NULL or EMPTY");
        }
        if (form.getTree() == null || form.getTree().isEmpty()) {
            throw new MissingParamException("version is NULL or EMPTY");
        }
        if (form.getStructure() == null || form.getStructure().isEmpty()) {
            throw new MissingParamException("structure is NULL or EMPTY");
        }
        if (form.getVisibility() == null || form.getVisibility().isEmpty()) {
            throw new MissingParamException("visibility is NULL or EMPTY");
        }

        Extension extension = new Extension();
        BeanUtils.copyProperties(form, extension);
        String id = hashExtension(extension);
        extension.setId(id);


        return extension;
    }

    @Override
    public String hashId(String group, String name, String version) {
        return HashUtil.hashHex(
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
