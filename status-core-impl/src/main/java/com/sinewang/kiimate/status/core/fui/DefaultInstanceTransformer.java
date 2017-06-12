package com.sinewang.kiimate.status.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.InsideView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */
@Component
public class DefaultInstanceTransformer implements InstanceTransformer {

    @Autowired
    private IntensionDai intensionDai;

    @Override
    public Map<String, Object> toTimedValue(List<InstanceDai.Record> instancesList, InsideView model) throws Panic, BadRequest {
        Map<String, List<InstanceDai.Record>> dict = dict(instancesList);
        IntensionDai.ChannelPubSet extension = ValueMapping.from(IntensionDai.ChannelPubSet.class, model);
        extension.setExtId(model.getRootId());
        extension.setPubSet(model.getSet());
        return parseTimed(extension, dict);
    }

    @Override
    public Map<String, Object> toRawValue(List<InstanceDai.Record> instancesList, InsideView model) throws Panic, BadRequest {
        Map<String, List<InstanceDai.Record>> dict = dict(instancesList);
        IntensionDai.ChannelPubSet extension = ValueMapping.from(IntensionDai.ChannelPubSet.class, model);
        extension.setExtId(model.getRootId());
        extension.setPubSet(model.getSet());
        return parseRaw(extension, dict);
    }

    private Map<String, Object> parseRaw(IntensionDai.ChannelPubSet pubSet, Map<String, List<InstanceDai.Record>> dict) throws Panic, BadRequest {
        List<IntensionDai.Record> records = intensionDai.loadLast(pubSet);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Record record : records) {
            if (record.getSingle()) {
                if (record.getRefSet() != null) {
                    IntensionDai.ChannelPubSet refPubSet = new IntensionDai.ChannelPubSet();
                    refPubSet.setPubSet(record.getRefSet());
                    Map<String, Object> child = parseRaw(refPubSet, dict);
                    if (!child.isEmpty()) {
                        result.put(record.getField(), child);
                    }
                } else {
                    List<InstanceDai.Record> instances = dict.get(record.getField());
                    if (instances != null && !instances.isEmpty()) {
                        Object value = dict.get(record.getField()).get(0).getValue();
                        if (value != null) {
                            result.put(record.getField(), value);
                        }
                    }
                }
            } else {
                if (record.getRefSet() != null) {
                    IntensionDai.ChannelPubSet refPubSet = new IntensionDai.ChannelPubSet();
                    refPubSet.setPubSet(record.getRefSet());
                    Map<String, Object> child = parseTimed(refPubSet, dict);
                    if (!child.isEmpty()) {
                        addComplexValueToList(result, record, child);
                    }
                } else {
                    List<InstanceDai.Record> instances = dict.get(record.getField());
                    if (instances != null && !instances.isEmpty()) {
                        for (InstanceDai.Record instance : instances) {
                            if (instance.getValue() != null) {
                                result.computeIfAbsent(instance.getField(), key -> new ArrayList<>());
                                List values = (List) result.get(record.getField());
                                values.add(instance.getValue());
                            }
                        }
                    }
                }

            }
        }
        return result;
    }


    private Map<String, Object> parseTimed(IntensionDai.ChannelPubSet pubSet, Map<String, List<InstanceDai.Record>> dict) throws Panic, BadRequest {
        List<IntensionDai.Record> records = intensionDai.loadLast(pubSet);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Record record : records) {
            if (record.getSingle()) {
                if (record.getRefSet() != null) {
                    IntensionDai.ChannelPubSet refPubSet = new IntensionDai.ChannelPubSet();
                    refPubSet.setPubSet(record.getRefSet());
                    Map<String, Object> child = parseTimed(refPubSet, dict);
                    if (!child.isEmpty()) {
                        result.put(record.getField(), child);
                    }
                } else {
                    List<InstanceDai.Record> instances = dict.get(record.getField());
                    if (instances != null && !instances.isEmpty()) {
                        Object value = dict.get(record.getField()).get(0).getValue();
                        if (value != null) {
                            TimedValue tv = new TimedValue();
                            tv.setValue(value);
                            tv.setTime(dict.get(record.getField()).get(0).getBeginTime());
                            result.put(record.getField(), tv);
                        }
                    }
                }
            } else {
                if (record.getRefSet() != null) {
                    IntensionDai.ChannelPubSet refPubSet = new IntensionDai.ChannelPubSet();
                    refPubSet.setPubSet(record.getRefSet());

                    Map<String, Object> child = parseTimed(refPubSet, dict);
                    addComplexValueToList(result, record, child);
                } else {
                    List<InstanceDai.Record> instances = dict.get(record.getField());
                    if (instances != null && !instances.isEmpty()) {
                        for (InstanceDai.Record instance : instances) {
                            if (instance.getValue() != null) {
                                Object v = instance.getValue();
                                TimedValue tv = new TimedValue();
                                tv.setValue(v);
                                tv.setTime(instance.getBeginTime());
                                result.computeIfAbsent(instance.getField(), key -> new ArrayList<>());
                                List values = (List) result.get(record.getField());
                                values.add(tv);
                            }
                        }
                    }
                }

            }
        }
        return result;
    }


    private void addComplexValueToList(Map<String, Object> result, IntensionDai.Record record, Map<String, Object> child) {
        if (!child.isEmpty()) {
            result.computeIfAbsent(record.getField(), key -> new ArrayList<>());
            List values = (List) result.get(record.getField());
            values.add(child);
        }
    }

    private Map<String, List<InstanceDai.Record>> dict(List<InstanceDai.Record> records) {
        Map<String, List<InstanceDai.Record>> dict = new HashMap<>();
        for (InstanceDai.Record record : records) {
            if (record.getValueSet() == null) {
                List<InstanceDai.Record> values = new ArrayList<>();
                values.add(record);
                dict.put(record.getField(), values);
            } else {
                dict.computeIfAbsent(record.getField(), key -> new ArrayList<>());
                List<InstanceDai.Record> values = dict.get(record.getField());
                values.add(record);
            }
        }
        return dict;
    }

}
