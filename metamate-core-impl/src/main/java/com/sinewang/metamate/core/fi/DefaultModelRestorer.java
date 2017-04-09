package com.sinewang.metamate.core.fi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.fi.AnModelRestorer;

import java.util.*;

/**
 * Created by WangYanJiong on 08/04/2017.
 */

@Component
public class DefaultModelRestorer implements AnModelRestorer {


    @Autowired
    private IntensionDai intensionDai;

    private List toArray(Object o) {
        List list = new ArrayList();
        list.add(o);
        return list;
    }

    public Map<String, Object> fullRestoreAsMap(String extId) {
        if (extId == null) {
            return Collections.emptyMap();
        }
        Map<String, Object> model = new HashMap<>();
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
        for (IntensionDai.Intension intension : intensions) {
            String refExtId = intension.getRefExtId();
            if (refExtId != null) {
                if (intension.isSingle()) {
                    model.put(intension.getField(), fullRestoreAsMap(refExtId));
                } else {
                    model.put(intension.getField(), toArray(fullRestoreAsMap(refExtId)));
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

    public void restoreAsFieldDict(String extId, Map<String, IntensionDai.Intension> map) {
        if (extId == null) {
            return;
        }
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
        for (IntensionDai.Intension intension : intensions) {
            String refExtId = intension.getRefExtId();
            if (refExtId != null) {
                restoreAsFieldDict(refExtId, map);
            } else {
                map.put(intension.getField(), intension);
            }
        }
    }
}
