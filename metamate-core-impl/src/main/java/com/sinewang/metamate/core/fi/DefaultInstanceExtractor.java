package com.sinewang.metamate.core.fi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.yanjiong.magnet.util.HashUtil;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnInstanceExtractor;
import wang.yanjiong.metamate.core.fi.AnIntensionExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@Service
public class DefaultInstanceExtractor implements AnInstanceExtractor {

    @Autowired
    private AnExtensionExtractor anExtensionExtractor;

    @Autowired
    private AnIntensionExtractor anIntensionExtractor;

    @Override
    public List<Instance> extract(String group, String name, String version, String ownerId, String operatorId, Map<String, String[]> map) {
        String extId = anExtensionExtractor.hashId(group, name, version);
        List<Instance> instances = new ArrayList<>();
        for (String field : map.keySet()) {
            String intId = anIntensionExtractor.hashId(extId, field);
            String[] values = map.get(field);
            String id = HashUtil.hashHex(intId, ownerId);
            Instance instance = new Instance();
            instance.setExtId(extId);
            instance.setField(field);
            instance.setValues(values);
            instance.setId(id);
            instance.setIntId(intId);
            instance.setOwnerId(ownerId);
            instance.setOperatorId(operatorId);
            instances.add(instance);
        }
        return instances;
    }


}
