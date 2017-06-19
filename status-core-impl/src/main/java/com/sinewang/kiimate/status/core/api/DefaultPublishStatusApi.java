package com.sinewang.kiimate.status.core.api;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.status.core.api.PublishStatusApi;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.ZoomInById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
@Component
public class DefaultPublishStatusApi implements PublishStatusApi {

    private static final Eid64Generator insgen = new Eid64Generator(5);


    private static final Eid64Generator pubset = new Eid64Generator(6);

    @Autowired
    private GlimpsesDai glimpsesDai;

    @Autowired
    private InstanceDai instanceDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic {


        ZoomInById id = ValueMapping.from(ZoomInById.class, form);
        id.setSubscriberId(form.getProviderId());

        List<InstanceDai.Record> records = instanceDai.loadInstances(id);

        List<GlimpsesDai.Entry> entries = new ArrayList<>();


        for (InstanceDai.Record instance : records) {
            GlimpsesDai.Entry record = new GlimpsesDai.Entry();
            record.setInsId(instance.getId());
            record.setId(insgen.born());
            entries.add(record);
        }

        GlimpsesDai.Publication record = ValueMapping.from(GlimpsesDai.Publication.class, form, context);
        record.setSet(pubset.born());
        record.setBeginTime(new Date());
        record.setModelSubId(form.getId());

        glimpsesDai.remember(record, entries);
        Receipt receipt = ValueMapping.from(Receipt.class, form, record);
        return NotBadResponse.of(receipt);
    }
}
