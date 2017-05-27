package com.sinewang.kiimate.status.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
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
    public Map<String, Object> toTimedValue(List<InstanceDai.Instance> instancesList, String rootExtId) {
        Map<String, List<InstanceDai.Instance>> dict = dict(instancesList);
        IntensionDai.ChannelExtension extension = new IntensionDai.ChannelExtension();
        extension.setId(rootExtId);
        return parseTimed(extension, dict);
    }

    @Override
    public Map<String, Object> toRawValue(List<InstanceDai.Instance> instancesList, String rootExtId) {
        Map<String, List<InstanceDai.Instance>> dict = dict(instancesList);
        IntensionDai.ChannelExtension extension = new IntensionDai.ChannelExtension();
        extension.setId(rootExtId);
        return parseRaw(extension, dict);
    }

    private Map<String, Object> parseRaw(IntensionDai.ChannelExtension extId, Map<String, List<InstanceDai.Instance>> dict) {
        List<IntensionDai.Intension> intensions = intensionDai.loadIntensions(extId);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Intension intension : intensions) {
            if (intension.isSingle()) {
                if (intension.getRefExtId() != null) {
                    IntensionDai.ChannelExtension refExtId = new IntensionDai.ChannelExtension();
                    refExtId.setId(intension.getRefExtId());
                    Map<String, Object> child = parseRaw(refExtId, dict);
                    if (!child.isEmpty()) {
                        result.put(intension.getField(), child);
                    }
                } else {
                    List<InstanceDai.Instance> instances = dict.get(intension.getField());
                    if (instances != null && !instances.isEmpty()) {
                        Object value = dict.get(intension.getField()).get(0).getValue();
                        if (value != null) {
                            result.put(intension.getField(), value);
                        }
                    }
                }
            } else {
                if (intension.getRefExtId() != null) {
                    IntensionDai.ChannelExtension refExtId = new IntensionDai.ChannelExtension();
                    refExtId.setId(intension.getRefExtId());
                    Map<String, Object> child = parseTimed(refExtId, dict);
                    if (!child.isEmpty()) {
                        addComplexValueToList(result, intension, child);
                    }
                } else {
                    List<InstanceDai.Instance> instances = dict.get(intension.getField());
                    if (instances != null && !instances.isEmpty()) {
                        for (InstanceDai.Instance instance : instances) {
                            if (instance.getValue() != null) {
                                result.computeIfAbsent(instance.getField(), key -> new ArrayList<>());
                                List values = (List) result.get(intension.getField());
                                values.add(instance.getValue());
                            }
                        }
                    }
                }

            }
        }
        return result;
    }


    private Map<String, Object> parseTimed(IntensionDai.ChannelExtension extId, Map<String, List<InstanceDai.Instance>> dict) {
        List<IntensionDai.Intension> intensions = intensionDai.loadIntensions(extId);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Intension intension : intensions) {
            if (intension.isSingle()) {
                if (intension.getRefExtId() != null) {
                    IntensionDai.ChannelExtension refExtId = new IntensionDai.ChannelExtension();
                    refExtId.setId(intension.getRefExtId());
                    Map<String, Object> child = parseTimed(refExtId, dict);
                    if (!child.isEmpty()) {
                        result.put(intension.getField(), child);
                    }
                } else {
                    List<InstanceDai.Instance> instances = dict.get(intension.getField());
                    if (instances != null && !instances.isEmpty()) {
                        Object value = dict.get(intension.getField()).get(0).getValue();
                        if (value != null) {
                            TimedValue tv = new TimedValue();
                            tv.setValue(value);
                            tv.setTime(dict.get(intension.getField()).get(0).getBeginTime());
                            result.put(intension.getField(), tv);
                        }
                    }
                }
            } else {
                if (intension.getRefExtId() != null) {
                    IntensionDai.ChannelExtension refExtId = new IntensionDai.ChannelExtension();
                    refExtId.setId(intension.getRefExtId());

                    Map<String, Object> child = parseTimed(refExtId, dict);
                    addComplexValueToList(result, intension, child);
                } else {
                    List<InstanceDai.Instance> instances = dict.get(intension.getField());
                    if (instances != null && !instances.isEmpty()) {
                        for (InstanceDai.Instance instance : instances) {
                            if (instance.getValue() != null) {
                                Object v = instance.getValue();
                                TimedValue tv = new TimedValue();
                                tv.setValue(v);
                                tv.setTime(instance.getBeginTime());
                                result.computeIfAbsent(instance.getField(), key -> new ArrayList<>());
                                List values = (List) result.get(intension.getField());
                                values.add(tv);
                            }
                        }
                    }
                }

            }
        }
        return result;
    }


    private void addComplexValueToList(Map<String, Object> result, IntensionDai.Intension intension, Map<String, Object> child) {
        if (!child.isEmpty()) {
            result.computeIfAbsent(intension.getField(), key -> new ArrayList<>());
            List values = (List) result.get(intension.getField());
            values.add(child);
        }
    }

    private Map<String, List<InstanceDai.Instance>> dict(List<InstanceDai.Instance> instances) {
        Map<String, List<InstanceDai.Instance>> dict = new HashMap<>();
        for (InstanceDai.Instance instance : instances) {
            if (instance.getValueSetHash() == null) {
                List<InstanceDai.Instance> values = new ArrayList<>();
                values.add(instance);
                dict.put(instance.getField(), values);
            } else {
                dict.computeIfAbsent(instance.getField(), key -> new ArrayList<>());
                List<InstanceDai.Instance> values = dict.get(instance.getField());
                values.add(instance);
            }
        }
        return dict;
    }

}
