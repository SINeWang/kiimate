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
        Map<String, Object> map = parseTimed(rootExtId, dict);
        return map;
    }

    @Override
    public Map<String, Object> toRawValue(List<InstanceDai.Instance> instancesList, String rootExtId) {
        Map<String, List<InstanceDai.Instance>> dict = dict(instancesList);
        Map<String, Object> map = parseRaw(rootExtId, dict);
        return map;
    }

    private Map<String, Object> parseRaw(String extId, Map<String, List<InstanceDai.Instance>> dict) {
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Intension intension : intensions) {
            if (intension.isSingle()) {
                if (intension.getRefExtId() != null) {
                    Map<String, Object> child = parseRaw(intension.getRefExtId(), dict);
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
                    Map<String, Object> child = parseTimed(intension.getRefExtId(), dict);
                    if (!child.isEmpty()) {
                        List values = (List) result.get(intension.getField());
                        if (values == null) {
                            // TODO: should be convert to TimedValue
                            values = new ArrayList<>();
                            values.add(child);
                            result.put(intension.getField(), values);
                        } else {
                            values.add(child);
                        }
                    }
                } else {
                    List<InstanceDai.Instance> instances = dict.get(intension.getField());
                    if (instances != null && !instances.isEmpty()) {
                        for (InstanceDai.Instance instance : instances) {
                            if (instance.getValue() != null) {
                                List values = (List) result.get(intension.getField());
                                if (values == null) {
                                    values = new ArrayList<>();
                                    values.add(instance.getValue());
                                    result.put(intension.getField(), values);
                                } else {
                                    values.add(instance.getValue());
                                }
                            }
                        }
                    }
                }

            }
        }
        return result;
    }


    private Map<String, Object> parseTimed(String extId, Map<String, List<InstanceDai.Instance>> dict) {
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Intension intension : intensions) {
            if (intension.isSingle()) {
                if (intension.getRefExtId() != null) {
                    Map<String, Object> child = parseTimed(intension.getRefExtId(), dict);
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
                    Map<String, Object> child = parseTimed(intension.getRefExtId(), dict);
                    if (!child.isEmpty()) {
                        List values = (List) result.get(intension.getField());
                        if (values == null) {
                            // TODO: should be convert to TimedValue
                            values = new ArrayList<>();
                            values.add(child);
                            result.put(intension.getField(), values);
                        } else {
                            values.add(child);
                        }
                    }
                } else {
                    List<InstanceDai.Instance> instances = dict.get(intension.getField());
                    if (instances != null && !instances.isEmpty()) {
                        for (InstanceDai.Instance instance : instances) {
                            if (instance.getValue() != null) {
                                List values = (List) result.get(intension.getField());
                                if (values == null) {
                                    values = new ArrayList<>();
                                    values.add(instance.getValue());
                                    result.put(intension.getField(), values);
                                } else {
                                    values.add(instance.getValue());
                                }
                            }
                        }
                    }
                }

            }
        }
        return result;
    }

    private Map<String, List<InstanceDai.Instance>> dict(List<InstanceDai.Instance> instances) {
        Map<String, List<InstanceDai.Instance>> dict = new HashMap<>();
        for (InstanceDai.Instance instance : instances) {
            if (instance.getValueSetHash() == null) {
                List<InstanceDai.Instance> values = new ArrayList<>();
                values.add(instance);
                dict.put(instance.getField(), values);
            } else {
                List<InstanceDai.Instance> values = dict.get(instance.getField());
                if (values == null) {
                    values = new ArrayList<>();
                    dict.put(instance.getField(), values);
                }
                values.add(instance);
            }
        }
        return dict;
    }

}
