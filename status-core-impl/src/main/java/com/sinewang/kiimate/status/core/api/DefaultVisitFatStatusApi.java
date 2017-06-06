package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitFatStatusApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 03/06/2017.
 */
@Component
public class DefaultVisitFatStatusApi implements VisitFatStatusApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Status visit(ReadContext context, StatusIdForm form) throws BadRequest, NotFound, Panic {
        InstanceDai.ChannelStatusId id = ValueMapping.from(InstanceDai.ChannelStatusId.class, form);

        List<InstanceDai.Record> records = instanceDai.loadInstances(id);

        ModelSubscriptionDai.StatusId statusId = ValueMapping.from(ModelSubscriptionDai.StatusId.class, context, id);

        ModelSubscriptionDai.ModelPubSet model = modelSubscriptionDai.getModelPubSetByStatusId(statusId);

        Status status = ValueMapping.from(Status.class, model);
        Map<String, Object> map = instanceTransformer.toTimedValue(records, model);

        IntensionDai.ChannelLastExtension rootExtension = ValueMapping.from(IntensionDai.ChannelLastExtension.class, model);
        rootExtension.setId(model.getRootExtId());
        List<IntensionDai.Record> recordList = intensionDai.loadLast(rootExtension);
        List<Intension> intensions = ValueMapping.from(Intension.class, recordList);
        status.setIntensions(intensions);
        status.setMap(map);
        return NotBadResponse.of(Status.class, MayHave.class, status);
    }
}
