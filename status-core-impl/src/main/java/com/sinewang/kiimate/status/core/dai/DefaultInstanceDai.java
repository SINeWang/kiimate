package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.InstanceMapper;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.status.core.dai.InstanceDai;
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

    private static final Eid64Generator idgen = new Eid64Generator(7);

    private static final Eid64Generator setgen = new Eid64Generator(8);

    @Autowired
    private InstanceMapper instanceMapper;

    @Override
    public void remember(List<Record> records) throws InstanceDuplicated {
        Date now = new Date();
        for (Record record : records) {
            String[] values = record.getValues();
            {
                if (values.length == 1) {
                    if (values[0].isEmpty()) {
                        instanceMapper.updateInstanceEndTimeBySubIdIntId(
                                record.getSubId(),
                                record.getIntId(),
                                now);
                        continue;
                    }
                    boolean refresh = false;
                    boolean insert = false;

                    List<Instance> latestInstanceList = instanceMapper.selectLatestInstanceBySubIdIntId(
                            record.getSubId(),
                            record.getIntId());
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
                                record.getSubId(),
                                record.getIntId(),
                                now);
                    }
                    if (insert) {
                        Instance instance = new Instance();
                        BeanUtils.copyProperties(record, instance);
                        instance.setValue(values[0]);
                        insertInstance(instance, now);
                    }
                    continue;
                }
            }

            {

                List<Instance> latestInstanceList = instanceMapper.selectLatestInstanceBySubIdIntId(
                        record.getSubId(),
                        record.getIntId());
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
                        record.getSubId(),
                        record.getIntId(),
                        now);
                long set = setgen.born();
                for (String value : values) {
                    Instance instance = new Instance();
                    BeanUtils.copyProperties(record, instance, "id");
                    instance.setValueSet(set);
                    instance.setValue(value);
                    instance.setId(idgen.born());
                    insertInstance(instance, now);
                }
            }
        }
    }

    @Override
    public List<Instance> loadInstances(ChannelAssetId channel) {
        return instanceMapper.selectLatestInstancesByAssetId(channel.getId());
    }

    @Override
    public List<Instance> loadInstances(ChannelStatusId channel) {
        return instanceMapper.selectLatestInstancesByStatusId(channel.getId());
    }


    private void insertInstance(Instance instance, Date beginTime) {
        if (instance.getValue().isEmpty()) {
            return;
        }
        instanceMapper.insertInstance(
                instance.getId(),
                instance.getCommit(),
                instance.getOwnerId(),
                instance.getSubId(),
                instance.getExtId(),
                instance.getIntId(),
                instance.getField(),
                instance.getValue(),
                instance.getValueSet(),
                instance.getValueRefPath(),
                instance.getValueRefPolicy(),
                instance.getOperatorId(),
                beginTime);
    }
}
