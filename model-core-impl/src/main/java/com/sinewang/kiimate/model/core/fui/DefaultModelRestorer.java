package com.sinewang.kiimate.model.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public Map<String, Object> restoreAsMetaData(IntensionDai.ChannelExtensionId extension) throws BadRequest, NotFound, Panic {
        Map<String, Object> model = new HashMap<>();
        List<IntensionDai.Record> records = intensionDai.loadLast(extension);
        for (IntensionDai.Record record : records) {
            Long refPubSet = record.getRefPubSet();
            if (refPubSet != null) {
                ModelPublicationDai.ChannelPubSet pubset = new ModelPublicationDai.ChannelPubSet();
                pubset.setPubSet(refPubSet);
                ModelPublicationDai.Record publication = modelPublicationDai.loadRootPublications(pubset);
                IntensionDai.ChannelExtensionId refExt = new IntensionDai.ChannelExtensionId();
                refExt.setBeginTime(publication.getBeginTime());
                refExt.setId(publication.getExtId());
                if (record.getSingle()) {
                    model.put(record.getField(), restoreAsMetaData(refExt));
                } else {
                    model.put(record.getField(), toArray(restoreAsMetaData(refExt)));
                }
            } else {
                if (record.getSingle()) {
                    model.put(record.getField(), record.getStructure());
                } else {
                    model.put(record.getField(), toArray(record.getStructure()));
                }
            }
        }
        return model;
    }


    public Map<String, IntensionDai.Record> restoreAsIntensionDict(IntensionDai.ChannelExtensionId extension) throws BadRequest, NotFound, Panic {
        Map<String, IntensionDai.Record> map = new HashMap<>();
        restoreAsFieldDict(extension, map);
        return map;
    }

    private void restoreAsFieldDict(IntensionDai.ChannelExtensionId extension, Map<String, IntensionDai.Record> map) throws BadRequest, NotFound, Panic {
        List<IntensionDai.Record> records = intensionDai.loadLast(extension);
        for (IntensionDai.Record record : records) {
            Long refPubSet = record.getRefPubSet();
            if (refPubSet != null) {
                ModelPublicationDai.ChannelPubSet pubset = new ModelPublicationDai.ChannelPubSet();
                pubset.setPubSet(refPubSet);
                ModelPublicationDai.Record publication = modelPublicationDai.loadRootPublications(pubset);
                IntensionDai.ChannelExtensionId channel = new IntensionDai.ChannelExtensionId();
                channel.setId(publication.getExtId());
                channel.setBeginTime(publication.getBeginTime());
                restoreAsFieldDict(channel, map);
            } else {
                map.put(record.getField(), record);
            }
        }
    }
}
