package com.sinewang.metamate.core.api;

import one.kii.summer.erest.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.VisitInstancesApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<Map<String, Object>> readInstancesByGroupNameVersion(
            @RequestHeader(value = "X-SUMMER-RequestId", required = false) String requestId,
            @RequestHeader("X-SUMMER-OwnerId") String ownerId,
            @RequestHeader(value = "X-SUMMER-VisitorId", required = false) String visitorId,
            @PathVariable("group") String group,
            @PathVariable("name") String name,
            @PathVariable("tree") String tree) {

        String extId = extensionFormParser.hashId(ownerId, group, name, tree);
        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceByOwnerIdExtId(extId, ownerId);
        Map<String, Object> map = new HashMap<>();
        for (InstanceDai.Instance daiInstance : instances) {
            map.put(daiInstance.getField(), daiInstance.getValue());
        }
        return Response.accepted(requestId, map, ownerId);
    }

}
