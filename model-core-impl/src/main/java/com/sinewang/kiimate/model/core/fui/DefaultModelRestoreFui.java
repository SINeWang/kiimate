package com.sinewang.kiimate.model.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.fui.AnModelRestoreFui;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 08/04/2017.
 */

@Component
public class DefaultModelRestoreFui implements AnModelRestoreFui {


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
            Long refPubSet = record.getRefSet();
            if (refPubSet != null) {
                ModelPublicationDai.ChannelSet pubset = new ModelPublicationDai.ChannelSet();
                pubset.setSet(refPubSet);
                List<ModelPublicationDai.Record> publications = modelPublicationDai.loadPublications(pubset);

                ModelPublicationDai.Record publication = publications.get(0);
                IntensionDai.ChannelExtensionId refExt = new IntensionDai.ChannelExtensionId();
                refExt.setBeginTime(publication.getBeginTime());
                refExt.setEndTime(record.getBeginTime());
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


    public MultiValueMap<String, IntensionDai.Record> restoreAsIntensionDict(IntensionDai.ChannelExtensionId extension) throws BadRequest, NotFound, Panic {
        MultiValueMap<String, IntensionDai.Record> map = new LinkedMultiValueMap<>();
        restoreAsFieldDict(extension, map);
        return map;
    }

    private void restoreAsFieldDict(IntensionDai.ChannelExtensionId extension, MultiValueMap<String, IntensionDai.Record> map) throws BadRequest, NotFound, Panic {
        List<IntensionDai.Record> records = intensionDai.loadLast(extension);
        for (IntensionDai.Record record : records) {
            Long refSet = record.getRefSet();
            if (refSet != null) {
                ModelPublicationDai.ChannelSet set = new ModelPublicationDai.ChannelSet();
                set.setSet(refSet);
                List<ModelPublicationDai.Record> publications = modelPublicationDai.loadPublications(set);

                ModelPublicationDai.Record publication = publications.get(0);
                IntensionDai.ChannelExtensionId refExt = new IntensionDai.ChannelExtensionId();
                refExt.setBeginTime(publication.getBeginTime());
                refExt.setEndTime(record.getBeginTime());
                refExt.setId(publication.getExtId());

                MultiValueMap<String, IntensionDai.Record> subMap = restoreAsIntensionDict(refExt);
                for (String key : subMap.keySet()) {
                    List<IntensionDai.Record> subRecords = subMap.get(key);
                    for (IntensionDai.Record subRecord : subRecords) {
                        map.add(record.getField(), subRecord);
                    }
                }
            } else {
                map.set(record.getField(), record);
            }
        }
    }
}
