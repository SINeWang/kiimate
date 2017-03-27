package com.sinewang.metamate.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.Context;
import wang.yanjiong.magnet.xi.boundary.Summary;
import wang.yanjiong.metamate.core.api.GetInstanceApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;

import java.util.*;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@RestController
public class DefaultGetInstancesApi implements GetInstanceApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AnExtensionExtractor extensionFormParser;


    @Override
    public Receipt readInstanceByGroupNameVersionWithOwner(@PathVariable("group") String group,
                                                           @PathVariable("name") String name,
                                                           @PathVariable("version") String version,
                                                           @RequestHeader("X-MM-Owner-Id") String ownerId,
                                                           @RequestHeader("X-MM-Operator-Id") String operatorId) {
        String extId = extensionFormParser.hashId(group, name, version);
        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceByOwnerIdExtId(extId, ownerId);

        Receipt receipt = new Receipt();
        Summary summary = new Summary();
        summary.setStatus(Summary.Status.ACCEPTED);
        summary.setTime(new Date());

        Context context = new Context();
        context.setProcessId(UUID.randomUUID().toString());
        receipt.setSummary(summary);

        receipt.setContext(context);

        receipt.setOwnerId(ownerId);

        Map<String, String> map = new HashMap<>();

        for (InstanceDai.Instance instance : instances) {
            String key = group + "." + name + "." + instance.getField();
            map.put(key, instance.getValue());
        }

        receipt.setInstance(map);
        return receipt;
    }
}
