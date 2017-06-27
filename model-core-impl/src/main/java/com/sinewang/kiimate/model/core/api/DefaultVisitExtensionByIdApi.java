package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitExtensionByIdApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnModelRestoreFui;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomInById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 27/6/17.
 */

@Component
public class DefaultVisitExtensionByIdApi implements VisitExtensionByIdApi {

    @Autowired
    private AnModelRestoreFui modelRestorer;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;


    @Override
    public Receipt visit(ReadContext context, ZoomInById form) throws BadRequest, NotFound, Panic {
        ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, form, context);

        ExtensionDai.Record record = extensionDai.loadLast(channel);

        Receipt receipt = ValueMapping.from(Receipt.class, record);

        IntensionDai.ChannelExtensionId latest = ValueMapping.from(IntensionDai.ChannelExtensionId.class, record);
        latest.setBeginTime(record.getBeginTime());
        latest.setEndTime(record.getEndTime());
        List<IntensionDai.Record> records = intensionDai.loadLast(latest);

        List<Intension> intensions = ValueMapping.from(Intension.class, records);
        receipt.setIntensions(intensions);
        receipt.setSchema(modelRestorer.restoreAsMetaData(latest));
        return receipt;
    }

}
