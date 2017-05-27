package com.sinewang.kiimate.model.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
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

    @SuppressWarnings("unchecked")
    private List toArray(Object o) {
        List list = new ArrayList();
        list.add(o);
        return list;
    }

    public Map<String, Object> restoreAsMetaData(String extId) {
        if (extId == null) {
            return Collections.emptyMap();
        }
        IntensionDai.ChannelExtension channel = new IntensionDai.ChannelExtension();
        channel.setId(extId);
        return restoreAsMetaData(channel);
    }

    private Map<String, Object> restoreAsMetaData(IntensionDai.ChannelExtension extension) {
        Map<String, Object> model = new HashMap<>();
        List<IntensionDai.Intension> intensions = intensionDai.loadIntensions(extension);
        for (IntensionDai.Intension intension : intensions) {
            String refExtId = intension.getRefExtId();
            if (refExtId != null) {
                IntensionDai.ChannelExtension refExt = new IntensionDai.ChannelExtension();
                refExt.setId(refExtId);
                if (intension.isSingle()) {
                    model.put(intension.getField(), restoreAsMetaData(refExtId));
                } else {
                    model.put(intension.getField(), toArray(restoreAsMetaData(refExtId)));
                }
            } else {
                if (intension.isSingle()) {
                    model.put(intension.getField(), intension.getStructure());
                } else {
                    model.put(intension.getField(), toArray(intension.getStructure()));
                }
            }
        }
        return model;
    }

    public Map<String, IntensionDai.Intension> restoreAsIntensionDict(String extId) {
        Map<String, IntensionDai.Intension> map = new HashMap<>();
        IntensionDai.ChannelExtension channel = new IntensionDai.ChannelExtension();
        channel.setId(extId);
        restoreAsFieldDict(channel, map);
        return map;
    }

    public Map<String, IntensionDai.Intension> restoreAsIntensionDict(IntensionDai.ChannelExtension extension) {
            Map<String, IntensionDai.Intension> map = new HashMap<>();
            restoreAsFieldDict(extension, map);
            return map;
    }

    private void restoreAsFieldDict(IntensionDai.ChannelExtension extension, Map<String, IntensionDai.Intension> map) {
        List<IntensionDai.Intension> intensions = intensionDai.loadIntensions(extension);
        for (IntensionDai.Intension intension : intensions) {
            String refExtId = intension.getRefExtId();
            if (refExtId != null) {
                IntensionDai.ChannelExtension channel = new IntensionDai.ChannelExtension();
                channel.setId(refExtId);
                restoreAsFieldDict(channel, map);
            } else {
                map.put(intension.getField(), intension);
            }
        }
    }
}
