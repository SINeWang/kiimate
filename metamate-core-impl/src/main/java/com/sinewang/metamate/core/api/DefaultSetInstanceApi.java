package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.bound.Context;
import one.kii.summer.bound.Summary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.SetInstanceApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.fi.AnExtensionExtractor;
import wang.yanjiong.metamate.core.fi.AnInstanceExtractor;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@RestController
public class DefaultSetInstanceApi implements SetInstanceApi {

    private static final Logger logger = LoggerFactory.getLogger(DefaultSetInstanceApi.class);

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AnInstanceExtractor instanceExtractor;


    @Autowired
    private AnExtensionExtractor extensionExtractor;

    @Override
    public Receipt saveInstanceViaFormUrlEncoded(@PathVariable("group") String group,
                                                 @PathVariable("name") String name,
                                                 @PathVariable("tree") String tree,
                                                 @RequestHeader("X-MM-Owner-Id") String ownerId,
                                                 @RequestHeader("X-MM-Operator-Id") String operatorId,
                                                 HttpServletRequest request) {

        String extId = extensionExtractor.hashId(group, name, tree);

        List<AnInstanceExtractor.Instance> instances = instanceExtractor.extract(group, name, tree, ownerId, operatorId, request.getParameterMap());

        List<InstanceDai.Instances> instances1 = DataTools.copy(instances, InstanceDai.Instances.class);

        try {
            instanceDai.insertInstances(instances1);
        } catch (InstanceDai.InstanceDuplicated instanceDuplicated) {
            logger.error("instanceDuplicated", instanceDuplicated);
        }

        List<InstanceDai.Instance> instances2 = instanceDai.selectLatestInstanceByOwnerIdExtId(extId, ownerId);

        Receipt receipt = new Receipt();
        List<Instance> instances3 = new ArrayList<>();

        for (InstanceDai.Instance instance2 : instances2) {
            Instance instance3 = DataTools.copy(instance2, Instance.class);
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
