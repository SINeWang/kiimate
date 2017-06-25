package com.sinewang.kiimate.status.core.dai;

import com.google.common.collect.ObjectArrays;
import com.sinewang.kiimate.status.core.dai.mapper.InstanceMapper;
import lombok.Data;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.ZoomInById;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    private static final String[] EMPTY_VALUE = new String[]{"_"};

    @Autowired
    private InstanceMapper instanceMapper;

    @Override
    public void remember(List<Value> instances) throws Conflict {
        Date now = new Date();
        for (Value instance : instances) {
            String[] values = instance.getValues();
            {
                if (instance.getGlimpseId() != null) {
                    if (values == null) {
                        values = EMPTY_VALUE;
                    }
                }
            }

            {
                if (values.length == 1) {
                    if (values[0].isEmpty()) {
                        instanceMapper.revokeValue(
                                instance.getSubId(),
                                instance.getIntId(),
                                now);
                        continue;
                    }
                    boolean refresh = false;
                    boolean insert = false;

                    List<Record> latestRecordList = instanceMapper.selectLatestRecordById(
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
                        instanceMapper.revokeValue(
                                instance.getSubId(),
                                instance.getIntId(),
                                now);
                    }
                    if (insert) {
                        Record record = ValueMapping.from(Record.class, instance);
                        record.setValue(values[0]);
                        insertInstance(record, now);
                    }
                    continue;
                }
            }

            {

                List<Record> latestRecordList = instanceMapper.selectLatestRecordById(
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
                instanceMapper.revokeValue(
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
    public List<Value> loadInstances(ZoomInById channel) throws Panic {
        List<Record> records = instanceMapper.selectLastInstancesById(
                channel.getSubscriberId(),
                channel.getId(),
                channel.getBeginTime(),
                channel.getEndTime()
        );
        List<Value> values = new ArrayList<>();
        for (Record record : records) {
            if (record.getGlimpseId() == null) {
                Value v = ValueMapping.from(Value.class, record);
                v.setValues(new String[]{record.getValue()});
                values.add(v);
                continue;
            } else {
                for (Value value : values) {
                    if (value.getField().equals(records)) {
                        String[] vs = ObjectArrays.concat(value.getValues(), record.getValue());
                        value.setValues(vs);
                        continue;
                    }
                }
                Value v = ValueMapping.from(Value.class, record);
                v.setValues(new String[]{record.getValue()});
                values.add(v);
            }
        }

        return NotBadResponse.of(values);
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
                record.getGlimpseId(),
                record.getOperatorId(),
                beginTime);
    }

    @Data
    public static class Record {

        private Long id;

        private String commit;

        private String ownerId;

        private Long subId;

        private Long extId;

        private Long intId;

        private String field;

        private String value;

        @MayHave
        private Long valueSet;

        @MayHave
        private Long glimpseId;

        private String operatorId;

        private Date beginTime;

        @MayHave
        private Date endTime;

    }
}
