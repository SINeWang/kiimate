package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.context.io.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yanjiong.metamate.core.api.VisitEntitiesApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.dai.ModelSubscriptionDai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

public class DefaultVisitEntitiesApi implements VisitEntitiesApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Map<String, Object> readInstancesByGroup(ReadContext context, SimpleForm simpleForm) {
        Form form = DataTools.copy(simpleForm, Form.class);
        form.setName(NAME_DEFAULT);
        form.setTree(TREE_MASTER);
        return readInstancesByGroupNameTree(context, form);
    }

    @Override
    public Map<String, Object> readInstancesByGroupNameTree(ReadContext context, Form form) {

        String subId = modelSubscriptionDai.getLatestSubIdBySubscriberIdGroupNameTree(context.getOwnerId(), form.getGroup(), form.getName(), form.getTree());

        String rootExtId = modelSubscriptionDai.getLatestRootExtIdBySubscriberIdGroupNameTree(
                context.getOwnerId(), form.getGroup(), form.getName(), form.getTree());

        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceBySubId(subId);

        Map<String, List<InstanceDai.Instance>> dict = dict(instances);

        return visitHierarchyInstance(rootExtId, dict);

    }

    private Map<String, Object> visitHierarchyInstance(String extId, Map<String, List<InstanceDai.Instance>> dict) {
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Intension intension : intensions) {
            if (intension.isSingle()) {
                if (intension.getRefExtId() != null) {
                    Map<String, Object> child = visitHierarchyInstance(intension.getRefExtId(), dict);
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
                    Map<String, Object> child = visitHierarchyInstance(intension.getRefExtId(), dict);
                    if (!child.isEmpty()) {
                        List values = (List) result.get(intension.getField());
                        if (values == null) {
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
