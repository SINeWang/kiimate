package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SearchExtensionsApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

        ExtensionDai.ClueGroup clue = ValueMapping.from(ExtensionDai.ClueGroup.class, form);
        List<ExtensionDai.Extension> extensionList = extensionDai.queryExtension(clue);

        List<Extension> extensions = ValueMapping.from(Extension.class, extensionList);

        List<Extension> list = new ArrayList<>();
        for (Extension extension : extensions) {
            if (extension.getTree().startsWith("release-")) {
                continue;
            } else if (extension.getTree().startsWith("snapshot-")) {
                continue;
            }
            list.add(extension);
        }
        return list;

    }


}
