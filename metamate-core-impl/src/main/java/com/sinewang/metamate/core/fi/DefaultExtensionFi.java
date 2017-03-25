package com.sinewang.metamate.core.fi;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.fi.ExtensionFi;
import wang.yanjiong.metamate.core.model.Context;
import wang.yanjiong.metamate.core.model.Extension;
import wang.yanjiong.metamate.core.util.MessageDigestUtil;

import java.util.UUID;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionFi implements ExtensionFi {

    @Override
    public Extension accept(CreateExtensionApi.Request request) {
        Context context = new Context();
        context.setProcessId(UUID.randomUUID().toString());
        request.setContext(context);

        Extension extension = new Extension();
        BeanUtils.copyProperties(request, extension);
        String id = hashExtension(extension);
        extension.setId(id);
        return extension;
    }

    private String hashExtension(Extension extension) {
        return MessageDigestUtil.hashHex(
                extension.getGroup(),
                extension.getName(),
                extension.getVersion(),
                extension.getVisibility(),
                extension.getDataStructure()
        );
    }

}
