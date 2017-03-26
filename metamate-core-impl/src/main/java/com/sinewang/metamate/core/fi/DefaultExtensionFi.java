package com.sinewang.metamate.core.fi;

import com.sinewang.metamate.core.util.HashUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.api.CreateExtensionApi;
import wang.yanjiong.metamate.core.fi.ExtensionFi;
import wang.yanjiong.metamate.core.model.Context;

import java.util.UUID;

/**
 * Created by WangYanJiong on 25/03/2017.
 */
@Service
public class DefaultExtensionFi implements ExtensionFi {

    @Override
    public CreateExtensionApi.Extension accept(CreateExtensionApi.Form form) {
        Context context = new Context();
        context.setProcessId(UUID.randomUUID().toString());
        form.setContext(context);

        CreateExtensionApi.Extension extension = new CreateExtensionApi.Extension();
        BeanUtils.copyProperties(form, extension);
        String id = hashExtension(extension);
        extension.setId(id);
        return extension;
    }

    private String hashExtension(CreateExtensionApi.Extension extension) {
        return HashUtil.hashHex(
                extension.getGroup(),
                extension.getName(),
                extension.getVersion(),
                extension.getVisibility(),
                extension.getDataStructure()
        );
    }

}
