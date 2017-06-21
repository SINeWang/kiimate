package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitExtensionApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnModelRestoreFui;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomInByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 4/5/17.
 */

@Component
public class DefaultVisitExtensionApi implements VisitExtensionApi {

    @Autowired
    private AnModelRestoreFui modelRestorer;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;


    @Override
    public Receipt visit(ReadContext context, ZoomInByName form) throws BadRequest, NotFound, Panic {
        ExtensionDai.ChannelName channel = ValueMapping.from(ExtensionDai.ChannelName.class, form, context);

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
