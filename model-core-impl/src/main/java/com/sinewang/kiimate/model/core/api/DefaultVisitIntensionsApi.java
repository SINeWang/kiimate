package com.sinewang.kiimate.model.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import one.kii.kiimate.model.core.api.VisitIntensionsApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;

import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultVisitIntensionsApi implements VisitIntensionsApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnExtensionExtractor anExtensionExtractor;

    @Autowired
    private ExtensionDai extensionDai;


    @Override
    public Extension readIntensionsByGroupNameVersion(ReadContext context, Form form) throws NotFound {

        String extId = anExtensionExtractor.hashId(context.getOwnerId(), form.getGroup(), form.getName(), form.getTree(), VISIBILITY_PUBLIC);

        ExtensionDai.Extension dbExtension = extensionDai.selectExtensionById(extId);

        if (dbExtension == null) {
            throw new NotFound(new String[]{context.getOwnerId(), form.getGroup(), form.getName(), form.getTree()});
        }

        Extension extension = DataTools.copy(dbExtension, Extension.class);

        extension.setExtId(extId);

        extension.setOwnerId(context.getOwnerId());
        extension.setGroup(form.getGroup());
        extension.setName(form.getName());
        extension.setTree(form.getTree());

        List<IntensionDai.Intension> list = intensionDai.selectIntensionsByExtId(extId);

        List<Intension> intensions = DataTools.copy(list, Intension.class);

        extension.setIntensions(intensions);
        return extension;
    }
}
