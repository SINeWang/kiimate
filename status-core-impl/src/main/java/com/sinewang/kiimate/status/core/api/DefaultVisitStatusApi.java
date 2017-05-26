package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.BasicCopy;
import one.kii.summer.beans.utils.MagicCopy;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultVisitStatusApi implements VisitStatusApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Receipt visit(ReadContext context, Form form) throws NotFound {

        String rootExtId = modelSubscriptionDai.getLatestRootExtIdByOwnerSubscription(context.getOwnerId(), form.getSubId());
        if (rootExtId == null) {
            throw new NotFound(new String[]{form.getOwnerId(), form.getSubId()});
        }

        List<InstanceDai.Instance> instances = instanceDai.selectLatestInstanceBySubId(form.getSubId());

        List<IntensionDai.Intension> intensionList = intensionDai.selectIntensionsByExtId(rootExtId);
        List<Intension> intensions = BasicCopy.from(Intension.class, intensionList);


        Map<String, Object> map = instanceTransformer.toTimedValue(instances, rootExtId);

        Receipt receipt = MagicCopy.from(Receipt.class, form, context);
        receipt.setMap(map);
        receipt.setIntensions(intensions);

        return receipt;
    }


}
