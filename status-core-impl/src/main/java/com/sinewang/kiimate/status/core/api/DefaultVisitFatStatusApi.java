package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitFatStatusApi;
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
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultVisitFatStatusApi implements VisitFatStatusApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private StatusDai statusDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Status visit(ReadContext context, ZoomOutByName form) throws NotFound, BadRequest, Panic {


        OutsideView outside = statusDai.loadDownstream(form);

        ZoomInById id = ValueMapping.from(ZoomInById.class, outside);
        id.setSubscriberId(outside.getProviderId());

        InsideView modelPubSet = modelSubscriptionDai.loadModelSubById(id);
        id.setEndTime(outside.getBeginTime());

        List<InstanceDai.Value> values = instanceDai.loadInstances(id);

        IntensionDai.ChannelPubSet pubSet = ValueMapping.from(IntensionDai.ChannelPubSet.class, modelPubSet);
        pubSet.setSet(modelPubSet.getSet());

        List<IntensionDai.Record> intensionList = intensionDai.loadLast(pubSet);

        List<Intension> intensions = ValueMapping.from(Intension.class, intensionList);

        Map<String, Object> map = instanceTransformer.toFatValue(values, modelPubSet);
        Status status = ValueMapping.from(Status.class, outside, form, modelPubSet);
        status.setMap(map);
        status.setIntensions(intensions);
        return status;
    }

}
