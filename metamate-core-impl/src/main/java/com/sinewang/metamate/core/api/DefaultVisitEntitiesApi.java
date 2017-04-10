package com.sinewang.metamate.core.api;

import one.kii.summer.erest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitEntitiesApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.dai.ModelSubscriptionDai;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@RestController
@RequestMapping("/v1")
public class DefaultVisitEntitiesApi implements VisitEntitiesApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    @RequestMapping(value = "/{ownerId}/entities/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = "X-SUMMER-RequestId", required = false) String requestId,
            @RequestHeader("X-MM-VisitorId") String visitorId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        String subId = modelSubscriptionDai.getLatestSubIdBySubscriberIdGroupNameTree(ownerId, group, name, tree);

        String rootExtId = modelSubscriptionDai.getLatestRootExtIdBySubscriberIdGroupNameTree(
                ownerId, group, name, tree);

        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceBySubId(subId);

        Map<String, InstanceDai.Instance> dict = dict(instances);

        Map<String, Object> result = visitHierarchyInstance(rootExtId, dict);

        return Response.ok(requestId, result);
    }

    private void visitFlatInstance(String extId, Map<String, Object> result, Map<String, InstanceDai.Instance> dict) {
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);

        for (IntensionDai.Intension intension : intensions) {
            if (intension.getRefExtId() != null) {
                visitFlatInstance(intension.getRefExtId(), result, dict);
            } else {
                InstanceDai.Instance instance = dict.get(intension.getField());
                if (instance != null) {
                    result.put(intension.getField(), dict.get(intension.getField()).getValue());
                }
            }
        }
    }

    private Map<String, Object> visitHierarchyInstance(String extId, Map<String, InstanceDai.Instance> dict) {
        List<IntensionDai.Intension> intensions = intensionDai.selectIntensionsByExtId(extId);
        Map<String, Object> result = new HashMap<>();
        for (IntensionDai.Intension intension : intensions) {
            if (intension.getRefExtId() != null) {
                Map<String, Object> child = visitHierarchyInstance(intension.getRefExtId(), dict);
                if (!child.isEmpty()) {
                    result.put(intension.getField(), child);
                }
            } else {
                InstanceDai.Instance instance = dict.get(intension.getField());
                if (instance != null) {
                    Object value = dict.get(intension.getField()).getValue();
                    if (value != null) {
                        result.put(intension.getField(), value);
                    }
                }
            }
        }
        return result;
    }

    private Map<String, InstanceDai.Instance> dict(List<InstanceDai.Instance> instances) {
        Map<String, InstanceDai.Instance> dict = new HashMap<>();
        for (InstanceDai.Instance instance : instances) {
            dict.put(instance.getField(), instance);
        }
        return dict;
    }


}
