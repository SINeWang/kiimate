package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SearchExtensionsApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 08/05/2017.
 */
@Component
public class DefaultSearchExtensionsApi implements SearchExtensionsApi {


    @Autowired
    private ExtensionDai extensionDai;

    @Override
    public List<Extension> search(ReadContext context, QueryForm form) {

        List<ExtensionDai.Extension> extensionList = extensionDai.queryExtensionsByOwnerGroup(form.getOwnerId(), form.getGroup());

        List<Extension> extensions = BasicCopy.from(Extension.class, extensionList);

        return extensions;

    }


}
