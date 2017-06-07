package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.InstanceMapper;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.xyz.ViewUpWithId;
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
    public void remember(List<Instance> instances) throws Conflict {
        Date now = new Date();
        for (Instance instance : instances) {
            String[] values = instance.getValues();
            {
                if (values.length == 1) {
                    if (values[0].isEmpty()) {
                        instanceMapper.updateInstanceEndTimeBySubIdIntId(
                                instance.getSubId(),
                                instance.getIntId(),
                                now);
                        continue;
                    }
                    boolean refresh = false;
                    boolean insert = false;

                    List<Record> latestRecordList = instanceMapper.selectLatestInstanceBySubIdIntId(
                            instance.getSubId(),
                            instance.getIntId());
                    if (latestRecordList.size() == 0) {
                        insert = true;
                    } else if (latestRecordList.size() == 1) {
                        Record latestRecord = latestRecordList.get(0);
                        if (latestRecord.getValue().equals(values[0])) {
                            continue;
                        } else {
                            refresh = true;
                            insert = true;
                        }
                    } else if (latestRecordList.size() > 1) {
                        refresh = true;
                        insert = true;
                    }


                    if (refresh) {
                        instanceMapper.updateInstanceEndTimeBySubIdIntId(
                                instance.getSubId(),
                                instance.getIntId(),
                                now);
                    }
                    if (insert) {
                        Record record = new Record();
                        BeanUtils.copyProperties(instance, record);
                        record.setValue(values[0]);
                        insertInstance(record, now);
                    }
                    continue;
                }
            }

            {

                List<Record> latestRecordList = instanceMapper.selectLatestInstanceBySubIdIntId(
                        instance.getSubId(),
                        instance.getIntId());
                boolean refresh = false;
                if (values.length != latestRecordList.size()) {
                    refresh = true;
                    Arrays.sort(values);
                } else {
                    Arrays.sort(values);
                    String[] latestValues = new String[values.length];
                    for (int i = 0; i < latestValues.length; i++) {
                        latestValues[i] = latestRecordList.get(i).getValue();
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
                        instance.getSubId(),
                        instance.getIntId(),
                        now);
                long set = setgen.born();
                for (String value : values) {
                    Record record = new Record();
                    BeanUtils.copyProperties(instance, record, "id");
                    record.setValueSet(set);
                    record.setValue(value);
                    record.setId(idgen.born());
                    insertInstance(record, now);
                }
            }
        }
    }

    @Override
    public List<Record> loadInstances(ChannelAssetId channel) {
        return instanceMapper.selectLatestInstancesByAssetId(channel.getId());
    }

    @Override
    public List<Record> loadInstances(ViewUpWithId channel) {
        return instanceMapper.selectLatestInstancesByStatusId(
                channel.getSubscriberId(),
                channel.getId()
        );
    }


    private void insertInstance(Record record, Date beginTime) {
        if (record.getValue().isEmpty()) {
            return;
        }
        instanceMapper.insertInstance(
                record.getId(),
                record.getCommit(),
                record.getOwnerId(),
                record.getSubId(),
                record.getExtId(),
                record.getIntId(),
                record.getField(),
                record.getValue(),
                record.getValueSet(),
                record.getValueRefId(),
                record.getValueRefPolicy(),
                record.getOperatorId(),
                beginTime);
    }
}
