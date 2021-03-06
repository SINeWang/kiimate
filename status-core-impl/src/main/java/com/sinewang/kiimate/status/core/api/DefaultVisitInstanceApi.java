package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitInstanceApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.InsideView;
import one.kii.summer.zoom.ZoomInById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 03/06/2017.
 */
@Component
public class DefaultVisitInstanceApi implements VisitInstanceApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Instance visit(ReadContext context, ZoomInById form) throws BadRequest, NotFound, Panic {

        List<InstanceDai.Value> values = instanceDai.loadInstances(form);
        ZoomInById statusId = ValueMapping.from(ZoomInById.class, context, form);

        InsideView model = modelSubscriptionDai.loadModelSubById(statusId);
        Instance instance = ValueMapping.from(Instance.class, model);

        IntensionDai.ChannelPubSet set = ValueMapping.from(IntensionDai.ChannelPubSet.class, model);
        set.setSet(model.getSet());

        List<IntensionDai.Record> records = intensionDai.loadLast(set);
        List<Intension> intensions = ValueMapping.from(Intension.class, records);

        Map<String, Object> map = instanceTransformer.toFatValue(values, model);
        instance.setIntensions(intensions);
        instance.setMap(map);
        return NotBadResponse.of(instance);
    }
}
