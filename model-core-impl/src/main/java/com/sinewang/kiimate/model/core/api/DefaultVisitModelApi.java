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
import one.kii.summer.xyz.VisitDownInsight;
import one.kii.summer.xyz.VisitDownWithSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 31/05/2017.
 */
@Component
public class DefaultVisitModelApi implements VisitModelApi {

    @Autowired
    private ModelPublicationDai modelPublicationDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;


    @Override
    public Model visit(ReadContext context, VisitModelForm form) throws BadRequest, NotFound, Panic {


        ExtensionDai.ChannelSet channelSet = ValueMapping.from(ExtensionDai.ChannelSet.class, form);
        ExtensionDai.Record record = extensionDai.loadLast(channelSet);

        IntensionDai.ChannelExtensionId extensionId = new IntensionDai.ChannelExtensionId();
        extensionId.setBeginTime(record.getEndTime());
        extensionId.setId(record.getId());

        List<IntensionDai.Record> recordList = intensionDai.loadLast(extensionId);
        List<VisitModelApi.Intension> intensions = ValueMapping.from(VisitModelApi.Intension.class, recordList);


        int subscriptions = modelSubscriptionDai.countModelSubscriptions(channelSet.getSet());
        VisitDownWithSet set = new VisitDownWithSet();
        set.setProviderId(record.getOwnerId());
        set.setPubSet(form.getSet());

        VisitDownInsight downInsight = modelSubscriptionDai.selectModelBySet(set);
        VisitModelApi.Model model = ValueMapping.from(VisitModelApi.Model.class,downInsight, record);

        model.setSubscriptions(subscriptions);
        model.setIntensions(intensions);

        return NotBadResponse.of(model);
    }
}
