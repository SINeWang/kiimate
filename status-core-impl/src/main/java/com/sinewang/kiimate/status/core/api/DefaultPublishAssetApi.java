package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.PublishAssetApi;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.AssetPublicationExtractor;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.MagicCopy;
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

    @Override
    public Receipt commit(WriteContext context, Form form) throws BadRequest, Conflict, NotFound {

        AssetPublicationExtractor.Informal informal = assetPublicationExtractor.extract(context, form);

        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceBySubId(form.getSubId());

        List<AssetPublicationDai.Record> records = new ArrayList<>();

        final String publishAssetId = HashTools.hashHex(form.getProviderId(), form.getSubId());

        List<String> instancesIds = new ArrayList<>();
        for (InstanceDai.Instance instance : instances) {
            String id = HashTools.hashHex(publishAssetId, instance.getId());
            AssetPublicationDai.Record record = BasicCopy.from(AssetPublicationDai.Record.class, informal);
            record.setInsId(instance.getId());
            record.setId(id);
            records.add(record);
            instancesIds.add(id);
        }

        String[] idArray = instancesIds.toArray(new String[0]);
        Arrays.sort(idArray);
        String pubSet = HashTools.hashHex(idArray);


        Date date = assetPublicationDai.insert(pubSet, records);
        Map map = new HashMap<>();
        map.put("pubSet", pubSet);
        map.put("beginTime", date);
        return MagicCopy.from(Receipt.class, form, map);
    }
}
