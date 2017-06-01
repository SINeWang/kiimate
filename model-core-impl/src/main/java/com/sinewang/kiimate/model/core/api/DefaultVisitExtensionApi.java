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
    private AnModelRestorer modelRestorer;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;


    @Override
    public Receipt visit(ReadContext context, Form form) throws NotFound {
        AnExtensionExtractor.Extension extension = ValueMapping.from(AnExtensionExtractor.Extension.class, form, context);
        ExtensionDai.ChannelCoordinate channel = ValueMapping.from(ExtensionDai.ChannelCoordinate.class, extension);

        ExtensionDai.Record record = extensionDai.loadLast(channel);

        Receipt receipt = ValueMapping.from(Receipt.class, record);


        IntensionDai.ChannelExtension channelExtension = ValueMapping.from(IntensionDai.ChannelExtension.class, extension);
        List<IntensionDai.Record> recordList = intensionDai.loadLatest(channelExtension);

        List<Intension> intensions = ValueMapping.from(Intension.class, recordList);
        receipt.setIntensions(intensions);
        receipt.setSchema(modelRestorer.restoreAsMetaData(record.getId()));
        return receipt;
    }

}
