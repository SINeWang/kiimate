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
import one.kii.summer.zoom.InsideView;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomInById;
import one.kii.summer.zoom.ZoomOutByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 20/6/17.
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
    public Map<String, Object> visit(ReadContext context, ZoomOutByName form) throws NotFound, BadRequest, Panic {

        OutsideView statusOutside = statusDai.loadDownstream(form);

        ZoomInById zoomInById = ValueMapping.from(ZoomInById.class, statusOutside);
        zoomInById.setSubscriberId(statusOutside.getProviderId());
        InsideView modelInside = modelSubscriptionDai.loadModelSubById(zoomInById);

        zoomInById.setEndTime(statusOutside.getBeginTime());
        List<InstanceDai.Value> values = instanceDai.loadInstances(zoomInById);

        return instanceTransformer.toRawValue(values, modelInside);
    }

}
