package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitRawStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.StatusDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.ViewDownInsight;
import one.kii.summer.xyz.ViewDownWithXyz;
import one.kii.summer.xyz.ViewUpWithId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultVisitRawStatusApi implements VisitRawStatusApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private StatusDai statusDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Map<String, Object> visit(ReadContext context, ViewDownWithXyz form) throws NotFound, BadRequest, Panic {


        ViewDownInsight downsights = statusDai.loadDownstream(form);

        ModelSubscriptionDai.StatusId statusId1 = ValueMapping.from(ModelSubscriptionDai.StatusId.class, downsights);

        ModelSubscriptionDai.ModelPubSet modelPubSet = modelSubscriptionDai.getModelPubSetByStatusId(statusId1);

        ViewUpWithId upId = ValueMapping.from(ViewUpWithId.class, downsights);

        List<InstanceDai.Record> records = instanceDai.loadInstances(upId);

        return instanceTransformer.toRawValue(records, modelPubSet);
    }

}
