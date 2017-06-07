package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitAssetApi;
import one.kii.kiimate.status.core.api.VisitStatusApi;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.dai.StatusDai;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultVisitAssetApi implements VisitAssetApi {

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AssetDai assetDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Map<String, Object> visit(ReadContext context, VisitUpWithId form) throws NotFound, BadRequest, Panic {


        AssetDai.Assets assets = assetDai.load(form);

        VisitUpWithXyz channel = ValueMapping.from(VisitUpWithXyz.class, assets);

        VisitUpInsight modelPubSet = modelSubscriptionDai.getModelPubSetByXyz(channel);

        VisitUpWithId instanceChannel = ValueMapping.from(VisitUpWithId.class, modelPubSet);

        List<InstanceDai.Record> records = instanceDai.loadInstances(instanceChannel);

        return instanceTransformer.toRawValue(records, modelPubSet);
    }

}
