package com.sinewang.metamate.core.api;

import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wang.yanjiong.metamate.core.api.VisitEntitiesApi;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.dai.InstanceDai;
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
    private ExtensionDai extensionDai;

    @Override
    @RequestMapping(value = "/{ownerId}/entities/{group}/{name}/{tree:.+}", method = RequestMethod.GET)
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @RequestHeader("X-MM-ExtId") String extId,
            @PathVariable("ownerId") String ownerId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        Map<String, Object> map = visitInstance(ownerId, extId);

        return ResponseFactory.accepted(map, ownerId);
    }

    private Map<String, Object> visitInstance(String ownerId, String extId) {
        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceByOwnerIdExtId(extId, ownerId);

        Map<String, Object> map = new HashMap<>();

        for (InstanceDai.Instance instance : instances) {

            ExtensionDai.Extension extension = extensionDai.selectExtensionById(instance.getExtId());

            if (extension.getStructure().toUpperCase().equals(AnStructureValidator.Structure.IMPORT.name())) {
                map.put(instance.getField(), visitInstance(ownerId, extId));
            } else {
                map.put(instance.getField(), instance.getValue());
            }
        }
        return map;

    }


}
