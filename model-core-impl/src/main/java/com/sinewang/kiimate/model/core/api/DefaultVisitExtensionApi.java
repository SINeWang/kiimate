package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.VisitUpWithXyz;
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
    public Receipt visit(ReadContext context, VisitUpWithXyz form) throws BadRequest, NotFound, Panic {
        ExtensionDai.ChannelCoordinate channel = ValueMapping.from(ExtensionDai.ChannelCoordinate.class, form, context);

        ExtensionDai.Record record = extensionDai.loadLast(channel);

        Receipt receipt = ValueMapping.from(Receipt.class, record);

        IntensionDai.ChannelLastExtension latest = ValueMapping.from(IntensionDai.ChannelLastExtension.class, record);
        List<IntensionDai.Record> records = intensionDai.loadLast(latest);

        List<Intension> intensions = ValueMapping.from(Intension.class, records);
        receipt.setIntensions(intensions);
        receipt.setSchema(modelRestorer.restoreAsMetaData(latest));
        return receipt;
    }

}
