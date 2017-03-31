package com.sinewang.metamate.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.Context;
import wang.yanjiong.magnet.xi.boundary.Summary;
import wang.yanjiong.metamate.core.api.GetInstancesApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.*;

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
    public Receipt readInstancesByGroupNameVersion(@PathVariable("group") String group,
                                                   @PathVariable("name") String name,
                                                   @PathVariable("tree") String tree) {

        String extId = extensionFormParser.hashId(group, name, tree);

        List<String> ownerIds = instanceDai.selectLatestInstancesByOwnerIds(extId);

        Receipt receipt = new Receipt();
        Summary summary = new Summary();
        summary.setStatus(Summary.Status.ACCEPTED);
        summary.setTime(new Date());

        Context context = new Context();
        context.setProcessId(UUID.randomUUID().toString());
        receipt.setSummary(summary);

        receipt.setContext(context);
        List<Instance> instanceList = new ArrayList<>();

        for (String ownerId : ownerIds) {
            List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceByOwnerIdExtId(extId, ownerId);
            Map<String, String> map = new HashMap<>();
            Instance instance = new Instance();
            for (InstanceDai.Instance daiInstance : instances) {
                String key = group + "." + name + "." + daiInstance.getField();
                map.put(key, daiInstance.getValue());
            }
            instance.setInstance(map);
            instance.setOwnerId(ownerId);
            instanceList.add(instance);
        }

        receipt.setInstances(instanceList);
        return receipt;
    }
}
