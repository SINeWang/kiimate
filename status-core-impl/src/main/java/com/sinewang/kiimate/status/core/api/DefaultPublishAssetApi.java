package com.sinewang.kiimate.status.core.api;

import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.PublishAssetApi;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.AssetsDai;
import one.kii.kiimate.status.core.fui.AssetPublicationExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
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
    private AssetPublicationDai assetPublicationDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AssetPublicationExtractor assetPublicationExtractor;

    @Autowired
    private AssetsDai assetsDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {

        AssetPublicationExtractor.Informal informal = assetPublicationExtractor.extract(context, form);

        AssetsDai.ChannelModelSubId channelModelSubId = ValueMapping.from(AssetsDai.ChannelModelSubId.class, informal);
        channelModelSubId.setOwnerId(informal.getProviderId());
        AssetsDai.Asset previous = assetsDai.load(channelModelSubId);


        InstanceDai.ChannelStatusPubSet statusPubSet = ValueMapping.from(InstanceDai.ChannelStatusPubSet.class, previous);

        List<InstanceDai.Instance> instances = instanceDai.loadInstances(statusPubSet);

        List<AssetPublicationDai.Entry> entries = new ArrayList<>();


        for (InstanceDai.Instance instance : instances) {
            AssetPublicationDai.Entry record = ValueMapping.from(AssetPublicationDai.Entry.class, informal);
            record.setInsId(instance.getId());
            record.setId(insgen.born());
            record.setVersion(informal.getVersion());
            entries.add(record);
        }

        AssetPublicationDai.Record record = ValueMapping.from(AssetPublicationDai.Record.class, context);
        record.setPubSet(pubset.born());
        record.setEntries(entries);
        record.setPrevious(previous);

        Date date = assetPublicationDai.save(record);
        Map map = new HashMap<>();
        ModelSubscriptionDai.ChannelSubId channel = ValueMapping.from(ModelSubscriptionDai.ChannelSubId.class, form);
        channel.setOwnerId(informal.getProviderId());
        ModelSubscriptionDai.ModelSubscription modelSubscription = modelSubscriptionDai.selectSubscription(channel);
        map.put("beginTime", date);
        return ValueMapping.from(Receipt.class, form, map, modelSubscription);
    }
}
