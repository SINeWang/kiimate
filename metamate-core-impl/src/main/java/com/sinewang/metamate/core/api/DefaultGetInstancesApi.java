package com.sinewang.metamate.core.api;

import one.kii.summer.bound.factory.ResponseFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.GetInstancesApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/31/17.
 */
@RestController
public class DefaultGetInstancesApi implements GetInstancesApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AnExtensionExtractor extensionFormParser;


    @Override
    public ResponseEntity<List<Instance>> readInstancesByGroupNameVersion(@PathVariable("group") String group,
                                                                          @PathVariable("name") String name,
                                                                          @PathVariable("tree") String tree) {

        String extId = extensionFormParser.hashId(group, name, tree);

        List<String> ownerIds = instanceDai.selectLatestInstancesByOwnerIds(extId);

        List<Instance> instanceList = new ArrayList<>();

        for (String ownerId : ownerIds) {
            List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceByOwnerIdExtId(extId, ownerId);
            Map<String, String> map = new HashMap<>();
            Instance instance = new Instance();
            for (InstanceDai.Instance daiInstance : instances) {
                map.put(daiInstance.getField(), daiInstance.getValue());
            }
            instance.setInstance(map);
            instanceList.add(instance);
        }


        return ResponseFactory.accepted(instanceList);
    }
}
