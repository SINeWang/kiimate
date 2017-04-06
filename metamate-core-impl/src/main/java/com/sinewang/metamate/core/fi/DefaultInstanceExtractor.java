package com.sinewang.metamate.core.fi;

import one.kii.summer.codec.utils.HashTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
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
    private AnIntensionExtractor anIntensionExtractor;

    @Override
    public List<Instance> extract(String ownerId, String providerId, String extId, String operatorId, Map<String, List<String>> map) {
        List<Instance> instances = new ArrayList<>();

        for (String field : map.keySet()) {
            String intId = anIntensionExtractor.hashId(extId, field);
            String[] values = cleanUpValues(map.get(field).toArray(new String[0]));
            String id = HashTools.hashHex(intId, ownerId);
            String[] both = StringUtils.mergeStringArrays(new String[]{id}, values);
            id = HashTools.hashHex(both);
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

    private String[] cleanUpValues(String[] values) {
        if (values.length == 0) {
            return null;
        }
        List<String> valueList = new ArrayList<>();
        boolean empty = false;
        for (String value : values) {
            if (value == null) {
                continue;
            } else if (value.trim().length() == 0) {
                empty = true;
                continue;
            }
            valueList.add(value);
        }
        if (empty && valueList.size() == 0) {
            valueList.add("");
        }
        return valueList.toArray(new String[]{});
    }


}
