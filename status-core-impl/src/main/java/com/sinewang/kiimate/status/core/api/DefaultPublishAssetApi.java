package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.PublishAssetApi;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.LoadAssetsDai;
import one.kii.kiimate.status.core.fui.AssetPublicationExtractor;
import one.kii.summer.beans.utils.HashTools;
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

    @Autowired
    private AssetPublicationDai assetPublicationDai;

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AssetPublicationExtractor assetPublicationExtractor;

    @Autowired
    private LoadAssetsDai loadAssetsDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {

        AssetPublicationExtractor.Informal informal = assetPublicationExtractor.extract(context, form);
        LoadAssetsDai.Assets previous = null;
        try {
            LoadAssetsDai.ChannelModelSubId channelModelSubId = ValueMapping.from(LoadAssetsDai.ChannelModelSubId.class, informal);
            channelModelSubId.setOwnerId(informal.getProviderId());
            previous = loadAssetsDai.fetchAssets(channelModelSubId);
        } catch (NotFound ignore) {
        }

        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceBySubId(form.getSubId());

        List<AssetPublicationDai.Entry> entries = new ArrayList<>();

        final String publishAssetId = HashTools.hashHex(informal.getProviderId(), informal.getModelSubId());

        List<String> instancesIds = new ArrayList<>();
        for (InstanceDai.Instance instance : instances) {
            String id = HashTools.hashHex(publishAssetId, instance.getId());
            AssetPublicationDai.Entry record = ValueMapping.from(AssetPublicationDai.Entry.class, informal);
            record.setInsId(instance.getId());
            record.setId(id);
            record.setVersion(informal.getVersion());
            entries.add(record);
            instancesIds.add(id);
        }

        String[] idArray = instancesIds.toArray(new String[0]);
        Arrays.sort(idArray);
        String pubSet = HashTools.hashHex(idArray);

        AssetPublicationDai.Record record = new AssetPublicationDai.Record();
        record.setPubSet(pubSet);
        record.setEntries(entries);
        record.setPrevious(previous);

        Date date = assetPublicationDai.save(record);
        Map map = new HashMap<>();
        ModelSubscriptionDai.ChannelSubId channel = ValueMapping.from(ModelSubscriptionDai.ChannelSubId.class, form);
        channel.setOwnerId(informal.getProviderId());
        ModelSubscriptionDai.ModelSubscription modelSubscription =  modelSubscriptionDai.selectSubscription(channel);
        map.put("beginTime", date);
        return ValueMapping.from(Receipt.class, form, map, modelSubscription);
    }
}
