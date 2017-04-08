package com.sinewang.metamate.core.api;

import one.kii.summer.erest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitEntitiesApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.dai.IntensionDai;
import wang.yanjiong.metamate.core.dai.ModelSubscriptionDai;
import wang.yanjiong.metamate.core.fi.AnStructureValidator;

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

        String extId  = modelSubscriptionDai.getLatestRootExtIdBySubscriberIdGroupNameTree(
                ownerId, group, name, tree);


        Map<String, Object> map = visitInstance(ownerId, extId);

        return Response.accepted(requestId, map, ownerId);
    }

    private Map<String, Object> visitInstance(String ownerId, String extId) {
        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceByOwnerIdExtId(ownerId, extId);

        Map<String, Object> map = new HashMap<>();

        for (InstanceDai.Instance instance : instances) {

            IntensionDai.Intension intension = intensionDai.selectIntensionByIntId(instance.getIntId());

            if (intension.getStructure().toUpperCase().equals(AnStructureValidator.Structure.IMPORT.name())) {
                map.put(instance.getField(), visitInstance(ownerId, extId));
            } else {
                map.put(instance.getField(), instance.getValue());
            }
        }
        return map;

    }


}
