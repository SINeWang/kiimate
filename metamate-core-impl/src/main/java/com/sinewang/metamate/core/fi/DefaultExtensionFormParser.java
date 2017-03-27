package com.sinewang.metamate.core.fi;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.magnet.util.HashUtil;
import wang.yanjiong.magnet.xi.boundary.Context;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.fi.AnExtensionFormParser;

import java.util.UUID;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionFormParser implements AnExtensionFormParser {

    @Override
    public Extension parse(CreateExtensionApi.Form form) {
        Context context = new Context();
        context.setProcessId(UUID.randomUUID().toString());
        form.setContext(context);

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
                extension.getVersion()
        );
    }


}
