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
        ModelPublicationDai.ChannelPubSet pubSet = ValueMapping.from(ModelPublicationDai.ChannelPubSet.class, form);
        ModelPublicationDai.Record publication = modelPublicationDai.loadRootPublications(pubSet);

        ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, publication);
        channel.setId(publication.getExtId());
        ExtensionDai.Record record = extensionDai.loadLast(channel);

        IntensionDai.ChannelPubSet pubSet1 = ValueMapping.from(IntensionDai.ChannelPubSet.class, publication, record);

        pubSet1.setExtId(record.getId());


        List<IntensionDai.Record> recordList = intensionDai.loadLast(pubSet1);
        List<VisitModelApi.Intension> intensions = ValueMapping.from(VisitModelApi.Intension.class, recordList);


        int subscriptions = modelSubscriptionDai.countModelSubscriptions(publication.getPubSet());

        VisitModelApi.Model model = ValueMapping.from(VisitModelApi.Model.class, publication, record);

        model.setRootExtId(String.valueOf(record.getId()));

        model.setSubscriptions(subscriptions);

        model.setIntensions(intensions);

        return model;
    }
}
