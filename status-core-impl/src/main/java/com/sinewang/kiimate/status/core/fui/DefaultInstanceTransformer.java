package com.sinewang.kiimate.status.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.StatusDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.InsideView;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomInById;
import one.kii.summer.zoom.ZoomOutByName;
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

    @Autowired
    private GlimpsesDai glimpsesDai;

    @Autowired
    private StatusDai statusDai;

    @Autowired
    private InstanceDai instanceDai;

    private String getReferenceValue(Long glimpseId, String subscriberId, String field) throws Panic, BadRequest {
        ZoomInById id = new ZoomInById();
        id.setId(glimpseId);
        id.setSubscriberId(subscriberId);
        GlimpsesDai.Publication publication = glimpsesDai.load(id);

        ZoomOutByName form = ValueMapping.from(ZoomOutByName.class, publication);

        OutsideView statusOutside = statusDai.load(form);

        ZoomInById zoomInById = ValueMapping.from(ZoomInById.class, statusOutside);
        zoomInById.setSubscriberId(statusOutside.getProviderId());


        zoomInById.setEndTime(statusOutside.getBeginTime());
        List<InstanceDai.Value> values = instanceDai.loadInstances(zoomInById);

        for (InstanceDai.Value record : values) {
            if (record.getField().equals(field)) {
                if (record.getValues().length == 1)
                    return record.getValues()[0];
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> toFatValue(List<InstanceDai.Value> values, InsideView model) throws Panic, BadRequest {
        Map<String, InstanceDai.Value> dict = dict(values);
        IntensionDai.ChannelPubSet extension = ValueMapping.from(IntensionDai.ChannelPubSet.class, model);

        extension.setSet(model.getSet());
        return parseFat(extension, dict);
    }

    @Override
    public Map<String, Object> toRawValue(List<InstanceDai.Value> values, InsideView model) throws Panic, BadRequest {
        Map<String, InstanceDai.Value> dict = dict(values);
        IntensionDai.ChannelPubSet extension = ValueMapping.from(IntensionDai.ChannelPubSet.class, model);

        extension.setSet(model.getSet());
        return parseRaw(extension, dict);
    }

    private Map<String, Object> parseRaw(IntensionDai.ChannelPubSet pubSet, Map<String, InstanceDai.Value> dict) throws Panic, BadRequest {
        List<IntensionDai.Record> records = intensionDai.loadLast(pubSet);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Record record : records) {
            if (record.getSingle()) {
                if (record.getRefSet() != null) {
                    IntensionDai.ChannelPubSet refPubSet = new IntensionDai.ChannelPubSet();
                    refPubSet.setSet(record.getRefSet());
                    Map<String, Object> child = parseRaw(refPubSet, dict);
                    if (!child.isEmpty()) {
                        result.put(record.getField(), child);
                    }
                } else {
                    InstanceDai.Value instanceValue = dict.get(record.getField());
                    if (instanceValue != null && instanceValue.getValues().length > 0) {
                        Object value = instanceValue.getValues()[0];
                        Long valueRefId = instanceValue.getGlimpseId();
                        if (valueRefId != null) {
                            value = getReferenceValue(valueRefId, instanceValue.getOwnerId(), String.valueOf(value));
                        }

                        if (value != null) {
                            result.put(record.getField(), value);
                        }
                    }
                }
            } else {
                if (record.getRefSet() != null) {
                    IntensionDai.ChannelPubSet refPubSet = new IntensionDai.ChannelPubSet();
                    refPubSet.setSet(record.getRefSet());
                    Map<String, Object> child = parseFat(refPubSet, dict);
                    if (!child.isEmpty()) {
                        addComplexValueToList(result, record, child);
                    }
                } else {
                    InstanceDai.Value instanceValue = dict.get(record.getField());
                    if (instanceValue != null && instanceValue.getValues().length > 0) {
                        result.computeIfAbsent(instanceValue.getField(), key -> new ArrayList<>());
                        List values = (List) result.get(record.getField());
                        for (String v : instanceValue.getValues()) {
                            values.add(v);
                        }
                    }

                }
            }
        }
        return result;
    }


    private Map<String, Object> parseFat(IntensionDai.ChannelPubSet pubSet, Map<String, InstanceDai.Value> dict) throws Panic, BadRequest {
        List<IntensionDai.Record> intensions = intensionDai.loadLast(pubSet);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Record intension : intensions) {
            if (intension.getSingle()) {
                InstanceDai.Value instances = dict.get(intension.getField());
                if (instances != null && instances.getValues().length > 0) {
                    Object value = dict.get(intension.getField()).getValues()[0];
                    Object glimpseId = dict.get(intension.getField()).getGlimpseId();
                    if (value != null) {
                        FatValue tv = new FatValue();
                        tv.setValue(value);
                        tv.setTime(dict.get(intension.getField()).getBeginTime());
                        if (glimpseId != null) {
                            tv.setGlimpseId(String.valueOf(glimpseId));
                        }
                        result.put(intension.getField(), tv);
                    }

                }
            } else {
                InstanceDai.Value instances = dict.get(intension.getField());
                if (instances != null && instances.getValues().length > 0) {
                    FatValue tv = new FatValue();
                    tv.setTime(instances.getBeginTime());
                    tv.setValue(instances.getValues());
                    result.put(instances.getField(), tv);
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

    private Map<String, InstanceDai.Value> dict(List<InstanceDai.Value> values) {
        Map<String, InstanceDai.Value> dict = new HashMap<>();
        for (InstanceDai.Value value : values) {
            dict.put(value.getField(), value);
        }
        return dict;
    }

}
