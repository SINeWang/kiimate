package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.InstanceMapper;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.beans.utils.HashTools;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultInstanceDai implements InstanceDai {

    @Autowired
    private InstanceMapper instanceMapper;

    @Override
    public void insertInstances(List<Instances> instancesList) throws InstanceDuplicated {
        Date now = new Date();
        for (Instances instances : instancesList) {
            String[] values = instances.getValues();
            {
                if (values.length == 1) {
                    if (values[0].isEmpty()) {
                        instanceMapper.updateInstanceEndTimeBySubIdIntId(
                                instances.getSubId(),
                                instances.getIntId(),
                                now);
                        continue;
                    }
                    boolean refresh = false;
                    boolean insert = false;

                    List<Instance> latestInstanceList = instanceMapper.selectLatestInstanceBySubIdIntId(
                            instances.getSubId(),
                            instances.getIntId());
                    if (latestInstanceList.size() == 0) {
                        insert = true;
                    } else if (latestInstanceList.size() == 1) {
                        Instance latestInstance = latestInstanceList.get(0);
                        if (latestInstance.getValue().equals(values[0])) {
                            continue;
                        } else {
                            refresh = true;
                            insert = true;
                        }
                    } else if (latestInstanceList.size() > 1) {
                        refresh = true;
                        insert = true;
                    }


                    if (refresh) {
                        instanceMapper.updateInstanceEndTimeBySubIdIntId(
                                instances.getSubId(),
                                instances.getIntId(),
                                now);
                    }
                    if (insert) {
                        Instance instance = new Instance();
                        BeanUtils.copyProperties(instances, instance);
                        instance.setValue(values[0]);
                        insertInstance(instance, now);
                    }
                    continue;
                }
            }

            {

                List<Instance> latestInstanceList = instanceMapper.selectLatestInstanceBySubIdIntId(
                        instances.getSubId(),
                        instances.getIntId());
                boolean refresh = false;
                if (values.length != latestInstanceList.size()) {
                    refresh = true;
                    Arrays.sort(values);
                } else {
                    Arrays.sort(values);
                    String[] latestValues = new String[values.length];
                    for (int i = 0; i < latestValues.length; i++) {
                        latestValues[i] = latestInstanceList.get(i).getValue();
                    }
                    Arrays.sort(latestValues);

                    for (int i = 0; i < latestValues.length; i++) {
                        if (latestValues[i] == null) {
                            refresh = true;
                            break;
                        }
                        if (!values[i].equals(latestValues[i])) {
                            refresh = true;
                            break;
                        }
                    }

                }
                if (!refresh) {
                    continue;
                }
                instanceMapper.updateInstanceEndTimeBySubIdIntId(
                        instances.getSubId(),
                        instances.getIntId(),
                        now);
                String valueSetHash = HashTools.hashHex(values);
                for (String value : values) {
                    Instance instance = new Instance();
                    BeanUtils.copyProperties(instances, instance, "id");
                    instance.setValueSetHash(valueSetHash);
                    instance.setValue(value);
                    String id = HashTools.hashHex(instances.getId(), value);
                    instance.setId(id);
                    insertInstance(instance, now);
                }
            }
        }
    }

    @Override
    public List<Instance> selectLatestInstanceBySubId(String subId) {
        return instanceMapper.selectLatestInstancesBySubId(subId);
    }


    private void insertInstance(Instance instance, Date beginTime) {
        if (instance.getValue().isEmpty()) {
            return;
        }
        instanceMapper.insertInstance(
                instance.getId(),
                instance.getOwnerId(),
                instance.getSubId(),
                instance.getExtId(),
                instance.getIntId(),
                instance.getField(),
                instance.getValue(),
                instance.getValueSetHash(),
                instance.getValueRefId(),
                instance.getOperatorId(),
                beginTime);
    }
}
