package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitModelApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 31/05/2017.
 */
@Component
public class DefaultVisitModelApi implements VisitModelApi {

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private ModelPublicationDai modelPublicationDai;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;


    @Override
    public Model visit(ReadContext context, VisitModelForm form) throws BadRequest, NotFound, Panic {


        ModelPublicationDai.ChannelSet channelSet = ValueMapping.from(ModelPublicationDai.ChannelSet.class, form);
        List<ModelPublicationDai.Record> publications = modelPublicationDai.loadPublications(channelSet);
        ModelPublicationDai.Record record = publications.get(0);

        IntensionDai.ChannelExtensionId extensionId = new IntensionDai.ChannelExtensionId();
        extensionId.setBeginTime(null);
        extensionId.setEndTime(record.getBeginTime());
        extensionId.setId(record.getExtId());

        List<IntensionDai.Record> recordList = intensionDai.loadLast(extensionId);
        List<VisitModelApi.Intension> intensions = ValueMapping.from(VisitModelApi.Intension.class, recordList);


        int subscriptions = modelSubscriptionDai.countModelSubscriptions(channelSet.getSet());
        ZoomOutBySet set = new ZoomOutBySet();
        set.setProviderId(record.getProviderId());
        set.setSet(form.getSet());

        OutsideView downInsight = modelSubscriptionDai.selectModelBySet(set);
        VisitModelApi.Model model = ValueMapping.from(VisitModelApi.Model.class, downInsight, record);

        model.setSubscriptions(subscriptions);
        model.setIntensions(intensions);

        return NotBadResponse.of(model);
    }
}
