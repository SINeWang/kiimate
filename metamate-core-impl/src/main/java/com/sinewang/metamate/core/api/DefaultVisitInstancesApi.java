package com.sinewang.metamate.core.api;

import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.VisitInstancesApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by WangYanJiong on 3/31/17.
 */
@RestController
public class DefaultVisitInstancesApi implements VisitInstancesApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AnExtensionExtractor extensionFormParser;


    @Override
    public ResponseEntity<Map<String, String>> readInstancesByGroupNameVersion(
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree,
            @RequestParam(value = "tag", defaultValue = "LATEST") String tag) {

        String extId = extensionFormParser.hashId(ownerId, group, name, tree);
        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceByOwnerIdExtId(extId, ownerId);
        Map<String, String> map = new HashMap<>();
        for (InstanceDai.Instance daiInstance : instances) {
            map.put(daiInstance.getField(), daiInstance.getValue());
        }
        return ResponseFactory.accepted(map, ownerId);
    }
}
