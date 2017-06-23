package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.fui.AnModelRestoreFui;
import one.kii.kiimate.status.core.api.RefreshEntireValueApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.kiimate.status.core.fui.AnInstanceExtractor;
import one.kii.kiimate.status.core.fui.InstanceTransformer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.InsideView;
import one.kii.summer.zoom.ZoomInById;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultRefreshEntireValueApi implements RefreshEntireValueApi {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRefreshEntireValueApi.class);

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private AnModelRestoreFui modelRestorer;

    @Autowired
    private AnInstanceExtractor instanceExtractor;

    @Autowired
    private InstanceTransformer instanceTransformer;

    @Override
    public Receipt commit(WriteContext context, SubIdForm form) throws BadRequest, Conflict, NotFound, Panic {
        ZoomInById channel = ValueMapping.from(ZoomInById.class, context, form);

        InsideView model = modelSubscriptionDai.loadModelSubById(channel);

        IntensionDai.ChannelExtensionId lastExtension = new IntensionDai.ChannelExtensionId();
        lastExtension.setId(model.getRootId());
        lastExtension.setEndTime(model.getBeginTime());

        MultiValueMap<String, IntensionDai.Record> dict = modelRestorer.restoreAsIntensionDict(lastExtension);

        List<InstanceDai.Instance> instances = instanceExtractor.extract(context, form, dict);

        for (InstanceDai.Instance instance : instances) {
            instance.setSubId(channel.getId());
        }
        instanceDai.remember(instances);


        IntensionDai.ChannelExtensionId rootExtension = ValueMapping.from(IntensionDai.ChannelExtensionId.class, model);


        rootExtension.setId(model.getRootId());


        ZoomInById id = ValueMapping.from(ZoomInById.class, channel);
        id.setSubscriberId(context.getOwnerId());
        List<InstanceDai.Record> newRecords = instanceDai.loadInstances(id);

        Map<String, Object> map = instanceTransformer.toFatValue(newRecords, model);

        Receipt receipt = ValueMapping.from(Receipt.class, form, context);
        receipt.setMap(map);

        return NotBadResponse.of(receipt);
    }

}
