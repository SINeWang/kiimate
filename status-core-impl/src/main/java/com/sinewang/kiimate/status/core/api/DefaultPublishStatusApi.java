package com.sinewang.kiimate.status.core.api;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.PublishStatusApi;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.AssetPublicationExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.xyz.ViewUpWithId;
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
    private AssetDai assetDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic {


        ViewUpWithId id = ValueMapping.from(ViewUpWithId.class, form);

        List<InstanceDai.Record> records = instanceDai.loadInstances(id);

        List<AssetDai.Entry> entries = new ArrayList<>();


        for (InstanceDai.Record instance : records) {
            AssetDai.Entry record = new AssetDai.Entry();
            record.setInsId(instance.getId());
            record.setId(insgen.born());
            entries.add(record);
        }

        AssetDai.Publication record = ValueMapping.from(AssetDai.Publication.class, form, context);
        record.setPubSet(pubset.born());
        record.setBeginTime(new Date());
        record.setModelSubId(form.getId());

        assetDai.remember(record, entries);
        ModelSubscriptionDai.StatusId channel = ValueMapping.from(ModelSubscriptionDai.StatusId.class, form);
        ModelSubscriptionDai.Status status = modelSubscriptionDai.selectSubscription(channel);
        Receipt receipt = ValueMapping.from(Receipt.class, form, status, record);
        return NotBadResponse.of(receipt);
    }
}
