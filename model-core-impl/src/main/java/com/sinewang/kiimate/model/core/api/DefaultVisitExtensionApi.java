package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 4/5/17.
 */

@Component
public class DefaultVisitExtensionApi implements VisitExtensionApi {

    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Autowired
    private AnModelRestorer modelRestorer;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;

    private Receipt buildReceipt(final String extId) {
        ExtensionDai.Extension extension = extensionDai.selectExtensionById(extId);
        Receipt receipt = DataTools.copy(extension, Receipt.class);

        List<IntensionDai.Intension> intensionList = intensionDai.selectIntensionsByExtId(extId);

        List<Intension> intensions = DataTools.copy(intensionList, Intension.class);
        receipt.setIntensions(intensions);
        receipt.setSchema(modelRestorer.restoreAsMetaData(extId));
        return receipt;
    }

    @Override
    public Receipt visit(ReadContext context, Form form) {
        AnExtensionExtractor.Extension extension = DataTools.copy(form, AnExtensionExtractor.Extension.class);
        extension.setOwnerId(context.getOwnerId());
        extension.setVisibility(VISIBILITY_PUBLIC);
        extensionExtractor.hashId(extension);
        return buildReceipt(extension.getId());
    }


    @Override
    public Receipt visit(ReadContext context, TinyForm form) {
        AnExtensionExtractor.Extension extension = DataTools.copy(form, AnExtensionExtractor.Extension.class);
        extension.setOwnerId(context.getOwnerId());
        extension.setName(NAME_ROOT);
        extension.setTree(TREE_MASTER);
        extension.setVisibility(VISIBILITY_PUBLIC);
        extensionExtractor.hashId(extension);
        return buildReceipt(extension.getId());
    }

    @Override
    public Receipt visit(ReadContext context, SimpleForm form) {
        AnExtensionExtractor.Extension extension = DataTools.copy(form, AnExtensionExtractor.Extension.class);
        extension.setOwnerId(context.getOwnerId());
        extension.setTree(TREE_MASTER);
        extension.setVisibility(VISIBILITY_PUBLIC);
        return buildReceipt(extension.getId());
    }
}
