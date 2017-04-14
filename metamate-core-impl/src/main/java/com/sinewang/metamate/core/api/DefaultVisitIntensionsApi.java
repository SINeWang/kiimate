package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yanjiong.metamate.core.api.VisitIntensionsApi;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

public class DefaultVisitIntensionsApi implements VisitIntensionsApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor anExtensionExtractor;


    @Override
    public Extension readIntensionsByGroupNameVersion(ReadContext context, Form form) {

        String extId = anExtensionExtractor.hashId(context.getOwnerId(), form.getGroup(), form.getName(), form.getTree(), VISIBILITY_PUBLIC);
        Extension extension = new Extension();
        extension.setExtId(extId);

        List<IntensionDai.Intension> list = intensionDai.selectIntensionsByExtId(extId);

        List<Intension> intensions = DataTools.copy(list, Intension.class);

        extension.setIntensions(intensions);
        return extension;
    }
}
