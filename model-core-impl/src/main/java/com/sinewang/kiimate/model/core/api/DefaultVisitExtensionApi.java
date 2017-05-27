package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.beans.utils.ValueMapping;
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
        ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, extension);

        String extId = extension.getId();

        ExtensionDai.Extension extensionRecord = extensionDai.loadExtension(channel);

        Receipt receipt = ValueMapping.from(Receipt.class, extensionRecord);


        IntensionDai.ChannelExtension channelExtension = ValueMapping.from(IntensionDai.ChannelExtension.class, extension);
        List<IntensionDai.Intension> intensionList = intensionDai.loadIntensions(channelExtension);

        List<Intension> intensions = ValueMapping.from(Intension.class, intensionList);
        receipt.setIntensions(intensions);
        receipt.setSchema(modelRestorer.restoreAsMetaData(extId));
        return receipt;
    }

    @Override
    public Receipt visit(ReadContext context, Form form) throws NotFound {
        AnExtensionExtractor.Extension extension = ValueMapping.from(AnExtensionExtractor.Extension.class, form, context);
        extensionExtractor.hashId(extension);
        return buildReceipt(extension);
    }

}
