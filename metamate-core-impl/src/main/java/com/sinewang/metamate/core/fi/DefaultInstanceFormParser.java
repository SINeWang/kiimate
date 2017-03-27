package com.sinewang.metamate.core.fi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.yanjiong.magnet.util.HashUtil;
import wang.yanjiong.metamate.core.fi.AnExtensionFormParser;
import wang.yanjiong.metamate.core.fi.AnInstanceFormParser;
import wang.yanjiong.metamate.core.fi.AnIntensionFormParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@Service
public class DefaultInstanceFormParser implements AnInstanceFormParser {

    @Autowired
    private AnExtensionFormParser anExtensionFormParser;

    @Autowired
    private AnIntensionFormParser anIntensionFormParser;

    @Override
    public List<Instance> parse(String group, String name, String version, String ownerId, String operatorId, Map<String, String[]> map) {
        String extId = anExtensionFormParser.hashId(group, name, version);
        List<Instance> instances = new ArrayList<>();
        for (String field : map.keySet()) {
            String intId = anIntensionFormParser.hashId(extId, field);
            String[] values = map.get(field);
            for(String value: values){
                String insId = HashUtil.hashHex(intId, ownerId, value);
                Instance instance = new Instance();
                instance.setExtId(extId);
                instance.setField(field);
                instance.setValue(value);
                instance.setId(insId);
                instance.setIntId(intId);
                instance.setOwnerId(ownerId);
                instance.setOperatorId(operatorId);
                instances.add(instance);
            }

        }
        return instances;
    }


}
