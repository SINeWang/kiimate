package com.sinewang.kiimate.model.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by WangYanJiong on 08/04/2017.
 */

@Component
public class DefaultModelRestorer implements AnModelRestorer {


    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelPublicationDai modelPublicationDai;

    @SuppressWarnings("unchecked")
    private List toArray(Object o) {
        List list = new ArrayList();
        list.add(o);
        return list;
    }

    public Map<String, Object> restoreAsMetaData(long extId) {
        if (extId == 0) {
            return Collections.emptyMap();
        }
        IntensionDai.ChannelExtension channel = new IntensionDai.ChannelExtension();
        channel.setId(extId);
        return restoreAsMetaData(channel);
    }

    private Map<String, Object> restoreAsMetaData(IntensionDai.ChannelExtension extension) {
        Map<String, Object> model = new HashMap<>();
        List<IntensionDai.Record> records = intensionDai.loadLatest(extension);
        for (IntensionDai.Record record : records) {
            long refPubSet = record.getRefPubSet();
            if (refPubSet != 0) {
                ModelPublicationDai.ChannelPubSet pubset = new ModelPublicationDai.ChannelPubSet();
                pubset.setPubSet(refPubSet);
                ModelPublicationDai.Publication publication = modelPublicationDai.loadRootPublications(pubset);
                if (record.isSingle()) {
                    model.put(record.getField(), restoreAsMetaData(publication.getExtId()));
                } else {
                    model.put(record.getField(), toArray(restoreAsMetaData(refPubSet)));
                }
            } else {
                if (record.isSingle()) {
                    model.put(record.getField(), record.getStructure());
                } else {
                    model.put(record.getField(), toArray(record.getStructure()));
                }
            }
        }
        return model;
    }

    public Map<String, IntensionDai.Record> restoreAsIntensionDict(long extId) {
        Map<String, IntensionDai.Record> map = new HashMap<>();
        IntensionDai.ChannelExtension channel = new IntensionDai.ChannelExtension();
        channel.setId(extId);
        restoreAsFieldDict(channel, map);
        return map;
    }

    private void restoreAsFieldDict(IntensionDai.ChannelExtension extension, Map<String, IntensionDai.Record> map) {
        List<IntensionDai.Record> records = intensionDai.loadLatest(extension);
        for (IntensionDai.Record record : records) {
            long refPubSet = record.getRefPubSet();
            if (refPubSet != 0) {
                ModelPublicationDai.ChannelPubSet pubset = new ModelPublicationDai.ChannelPubSet();
                pubset.setPubSet(refPubSet);
                ModelPublicationDai.Publication publication = modelPublicationDai.loadRootPublications(pubset);
                IntensionDai.ChannelExtension channel = new IntensionDai.ChannelExtension();
                channel.setId(publication.getExtId());
                restoreAsFieldDict(channel, map);
            } else {
                map.put(record.getField(), record);
            }
        }
    }
}
