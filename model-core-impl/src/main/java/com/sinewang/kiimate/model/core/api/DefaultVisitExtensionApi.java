package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
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

    private Receipt buildReceipt(final AnExtensionExtractor.Extension extension) throws NotFound {
        String extId = extension.getId();
        ExtensionDai.Extension extensionRecord = extensionDai.selectExtensionById(extId);
        if (extensionRecord == null) {
            throw new NotFound(KeyFactorTools.find(extension));
        }
        Receipt receipt = DataTools.copy(extensionRecord, Receipt.class);

        List<IntensionDai.Intension> intensionList = intensionDai.selectIntensionsByExtId(extId);

        List<Intension> intensions = DataTools.copy(intensionList, Intension.class);
        receipt.setIntensions(intensions);
        receipt.setSchema(modelRestorer.restoreAsMetaData(extId));
        return receipt;
    }

    @Override
    public Receipt visit(ReadContext context, Form form) throws NotFound {
        AnExtensionExtractor.Extension extension = DataTools.magicCopy(AnExtensionExtractor.Extension.class, form, context);
        extensionExtractor.hashId(extension);
        return buildReceipt(extension);
    }

}
