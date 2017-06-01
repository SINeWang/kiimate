package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitIntensionsApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnExtensionExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultVisitIntensionsApi implements VisitIntensionsApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ExtensionDai extensionDai;


    @Override
    public Receipt visit(ReadContext context, Form form) throws NotFound {

        AnExtensionExtractor.Extension extension = ValueMapping.from(AnExtensionExtractor.Extension.class, form);
        extension.setOwnerId(context.getOwnerId());
        extension.setVisibility(VISIBILITY_PUBLIC);

        ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, extension);

        ExtensionDai.Record dbRecord = extensionDai.loadLast(channel);

        if (dbRecord == null) {
            throw new NotFound(new String[]{context.getOwnerId(), form.getGroup(), form.getName(), form.getTree()});
        }

        IntensionDai.ChannelExtension channel1 = ValueMapping.from(IntensionDai.ChannelExtension.class, extension);
        List<IntensionDai.Record> list = intensionDai.loadLatest(channel1);

        List<Intension> intensions = ValueMapping.from(Intension.class, list);

        Receipt receipt = ValueMapping.from(Receipt.class, extension);
        receipt.setIntensions(intensions);
        return receipt;
    }
}
