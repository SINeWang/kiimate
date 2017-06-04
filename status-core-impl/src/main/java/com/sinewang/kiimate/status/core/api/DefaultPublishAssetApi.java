package com.sinewang.kiimate.status.core.api;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.PublishAssetApi;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.StatusDai;
import one.kii.kiimate.status.core.fui.AssetPublicationExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
@Component
public class DefaultPublishAssetApi implements PublishAssetApi {

    private static final Eid64Generator insgen = new Eid64Generator(5);


    private static final Eid64Generator pubset = new Eid64Generator(6);

    @Autowired
    private AssetDai assetDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AssetPublicationExtractor assetPublicationExtractor;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound, Panic {

        AssetPublicationExtractor.Informal informal = assetPublicationExtractor.extract(context, form);

        AssetDai.ChannelModelSubId channelModelSubId = ValueMapping.from(AssetDai.ChannelModelSubId.class, informal);
        channelModelSubId.setOwnerId(informal.getProviderId());
        AssetDai.Assets previous = assetDai.load(channelModelSubId);


        InstanceDai.ChannelStatusPubSet statusPubSet = ValueMapping.from(InstanceDai.ChannelStatusPubSet.class, previous);

        List<InstanceDai.Instance> instances = instanceDai.loadInstances(statusPubSet);

        List<StatusDai.Entry> entries = new ArrayList<>();


        for (InstanceDai.Instance instance : instances) {
            StatusDai.Entry record = ValueMapping.from(StatusDai.Entry.class, informal);
            record.setInsId(instance.getId());
            record.setId(insgen.born());
            record.setVersion(informal.getVersion());
            entries.add(record);
        }

        AssetDai.Publication record = ValueMapping.from(AssetDai.Publication.class, context);
        record.setPubSet(pubset.born());
        record.setEntries(entries);

        Date date = assetDai.remember(record);
        Map map = new HashMap<>();
        ModelSubscriptionDai.ChannelSubId channel = ValueMapping.from(ModelSubscriptionDai.ChannelSubId.class, form);
        channel.setOwnerId(informal.getProviderId());
        ModelSubscriptionDai.Status status = modelSubscriptionDai.selectSubscription(channel);
        map.put("beginTime", date);
        return ValueMapping.from(Receipt.class, form, map, status);
    }
}
