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

    static final private String PREFIX_RELEASE = "release";

    static final private String PREFIX_SNAPSHOT = "snapshot";

    @Autowired
    private ExtensionDai extensionDai;

    @Override
    public List<Extension> search(ReadContext context, QueryForm form) {

        ExtensionDai.ClueGroup clue = ValueMapping.from(ExtensionDai.ClueGroup.class, form);
        List<ExtensionDai.Record> recordList = extensionDai.search(clue);

        List<Extension> extensions = ValueMapping.from(Extension.class, recordList);

        List<Extension> list = new ArrayList<>();
        for (Extension extension : extensions) {
            if (extension.getTree().startsWith(PREFIX_RELEASE)) {
                continue;
            }
            if (extension.getTree().startsWith(PREFIX_SNAPSHOT)) {
                continue;
            }
            list.add(extension);
        }
        return list;

    }


}
