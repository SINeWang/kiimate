package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitIntensionsApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
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
    public Receipt visit(ReadContext context, Form form) throws BadRequest, NotFound, Panic {

        ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, form, context);

        ExtensionDai.Record extension = extensionDai.loadLast(channel);

        IntensionDai.ChannelExtensionId channel1 = ValueMapping.from(IntensionDai.ChannelExtensionId.class, extension);
        List<IntensionDai.Record> list = intensionDai.loadLast(channel1);

        List<Intension> intensions = ValueMapping.from(Intension.class, list);

        Receipt receipt = ValueMapping.from(Receipt.class, extension);
        receipt.setIntensions(intensions);
        return receipt;
    }
}
