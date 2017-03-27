package com.sinewang.metamate.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.magnet.xi.boundary.Context;
import wang.yanjiong.magnet.xi.boundary.Summary;
import wang.yanjiong.metamate.core.api.CreateInstanceApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.fi.AnExtensionFormParser;
import wang.yanjiong.metamate.core.fi.AnInstanceFormParser;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@RestController
public class DefaultCreateInstanceApi implements CreateInstanceApi {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCreateInstanceApi.class);

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AnInstanceFormParser instanceFormParser;


    @Autowired
    private AnExtensionFormParser extensionFormParser;

    @Override
    public Receipt createInstanceViaFormUrlEncoded(@PathVariable("group") String group,
                                                   @PathVariable("name") String name,
                                                   @PathVariable("version") String version,
                                                   @RequestHeader("X-MM-Owner-Id") String ownerId,
                                                   @RequestHeader("X-MM-Operator-Id") String operatorId,
                                                   HttpServletRequest request) {

        String extId = extensionFormParser.hashId(group, name, version);

        List<AnInstanceFormParser.Instance> instances = instanceFormParser.parse(group, name, version, ownerId, operatorId, request.getParameterMap());

        List<InstanceDai.Instance> instances1 = new ArrayList<>();

        for (AnInstanceFormParser.Instance instance : instances) {
            InstanceDai.Instance instance1 = new InstanceDai.Instance();
            BeanUtils.copyProperties(instance, instance1);

            if (instance.getValue().length == 1) {
                instance1.setValue(instance.getValue()[0]);
                instances1.add(instance1);
            }

        }

        try {
            instanceDai.insertInstances(instances1);
        } catch (InstanceDai.InstanceDuplicated instanceDuplicated) {
            instanceDuplicated.printStackTrace();
        }

        List<InstanceDai.Instance> instances2 = instanceDai.selectLatestInstancesByOwnerIdExtId(extId, ownerId);

        Receipt receipt = new Receipt();
        List<Instance> instances3 = new ArrayList<>();

        for (InstanceDai.Instance instance2 : instances2) {
            Instance instance3 = new Instance();
            BeanUtils.copyProperties(instance2, instance3);
            instance3.setValue(new String[]{instance2.getValue()});
            instances3.add(instance3);
        }
        receipt.setInstances(instances3);

        Summary summary = new Summary();
        summary.setStatus(Summary.Status.ACCEPTED);
        summary.setTime(new Date());

        Context context = new Context();

        context.setProcessId(UUID.randomUUID().toString());

        receipt.setContext(context);
        receipt.setSummary(summary);
        return receipt;
    }
}
