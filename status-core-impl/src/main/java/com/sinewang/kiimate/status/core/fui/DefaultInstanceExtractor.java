package com.sinewang.kiimate.status.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.api.RefreshEntireValueApi;
import one.kii.kiimate.status.core.api.RefreshPartialValueApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.AnInstanceExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.*;


/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultInstanceExtractor implements AnInstanceExtractor {

    private static final Eid64Generator setgen = new Eid64Generator(4);
    private static Logger logger = LoggerFactory.getLogger(DefaultInstanceExtractor.class);

    @Override
    public List<InstanceDai.Value> extract(WriteContext context, RefreshEntireValueApi.SubIdForm form, MultiValueMap<String, IntensionDai.Record> dict) {
        List<InstanceDai.Value> instances = new ArrayList<>();
        Date now = new Date();
        Map<String, List<String>> map = form.getMap();
        for (String field : map.keySet()) {
            String dictField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, field);
            IntensionDai.Record record = dict.getFirst(dictField);
            if (record == null) {
                logger.warn("cannot find field [{}]", field);
                continue;
            }
            String intId = record.getId();

            String[] values = cleanUpValues(map.get(field).toArray(new RefreshPartialValueApi.Value[0]));
            InstanceDai.Value value = ValueMapping.from(InstanceDai.Value.class, context, form);
            value.setId(String.valueOf(setgen.born()));
            value.setExtId(record.getExtId());
            value.setIntId(intId);
            value.setField(dictField);
            value.setValues(values);
            value.setBeginTime(now);
            value.setCommit(HashTools.hashHex(value));
            instances.add(value);
        }
        return instances;
    }

    @Override
    public List<InstanceDai.Value> extract(WriteContext context, RefreshPartialValueApi.SubIdForm form, MultiValueMap<String, IntensionDai.Record> dict) {
        List<InstanceDai.Value> values = new ArrayList<>();
        Date now = new Date();

        String dictField = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getField());
        IntensionDai.Record record = dict.getFirst(dictField);
        if (record == null) {
            logger.warn("cannot find field [{}]", form.getField());
            return Collections.emptyList();
        }
        String intId = record.getId();

        String[] vs = cleanUpValues(form.getValues());
        for (RefreshPartialValueApi.Value value : form.getValues()) {
            InstanceDai.Value instance = ValueMapping.from(InstanceDai.Value.class, context, form);
            instance.setId(String.valueOf(setgen.born()));
            instance.setExtId(record.getExtId());
            instance.setIntId(intId);
            instance.setField(dictField);
            if (value.getGlimpseId() != null) {
                instance.setGlimpseId(value.getGlimpseId());
            }
            instance.setValues(vs);
            instance.setBeginTime(now);
            instance.setCommit(HashTools.hashHex(instance));
            values.add(instance);
        }
        return values;
    }


    private String[] cleanUpValues(RefreshPartialValueApi.Value[] values) {
        if (values == null) {
            return null;
        }
        List<String> valueList = new ArrayList<>();
        boolean empty = false;
        for (RefreshPartialValueApi.Value value : values) {
            if (value == null) {
                continue;
            } else if (value.getValue().trim().length() == 0) {
                empty = true;
                continue;
            }
            valueList.add(value.getValue());
        }
        if (empty && valueList.size() == 0) {
            valueList.add("");
        }
        return valueList.toArray(new String[]{});
    }


}
