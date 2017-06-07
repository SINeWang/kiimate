package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.StatusDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.VisitDownInsight;
import one.kii.summer.xyz.VisitDownWithXyz;
import one.kii.summer.xyz.VisitUpInsight;
import one.kii.summer.xyz.VisitUpWithId;
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
    private StatusDai statusDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Map<String, Object> visit(ReadContext context, VisitDownWithXyz form) throws NotFound, BadRequest, Panic {


        VisitDownInsight downsights = statusDai.loadDownstream(form);

        VisitUpWithId id = ValueMapping.from(VisitUpWithId.class, downsights);
        id.setSubscriberId(downsights.getProviderId());

        VisitUpInsight modelPubSet = modelSubscriptionDai.getModelPubSetByStatusId(id);

        List<InstanceDai.Record> records = instanceDai.loadInstances(id);

        return instanceTransformer.toRawValue(records, modelPubSet);
    }

}
