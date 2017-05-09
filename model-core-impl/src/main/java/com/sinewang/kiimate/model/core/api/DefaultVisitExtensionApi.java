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
        String extId = extensionExtractor.hashId(context.getOwnerId(), form.getGroup(), form.getName(), form.getTree(), VISIBILITY_PUBLIC);
        return buildReceipt(extId);
    }


    @Override
    public Receipt visit(ReadContext context, TinyForm form) {
        String extId = extensionExtractor.hashId(context.getOwnerId(), form.getGroup(), NAME_ROOT, TREE_MASTER, VISIBILITY_PUBLIC);
        return buildReceipt(extId);
    }

    @Override
    public Receipt visit(ReadContext context, SimpleForm form) {
        String extId = extensionExtractor.hashId(context.getOwnerId(), form.getGroup(), form.getName(), TREE_MASTER, VISIBILITY_PUBLIC);
        return buildReceipt(extId);
    }
}
