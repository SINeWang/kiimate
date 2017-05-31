package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.VisitModelApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
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
    public Model visit(ReadContext context, VisitModelForm form) throws NotFound {
        ModelPublicationDai.Publication publication = modelPublicationDai.fetchPublicationsByPubSet(form.getPubSet());

        ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, publication);
        channel.setId(publication.getExtId());
        ExtensionDai.Extension extension;
        try {
            extension = extensionDai.loadLastExtension(channel);
        } catch (NotFound notFound) {
            throw notFound;
        }

        IntensionDai.ChannelPubSet pubSet = ValueMapping.from(IntensionDai.ChannelPubSet.class, publication, extension);


        List<IntensionDai.Intension> intensionList = intensionDai.loadLastIntensions(pubSet);
        List<VisitModelApi.Intension> intensions = ValueMapping.from(VisitModelApi.Intension.class, intensionList);


        int subscriptions = modelSubscriptionDai.countModelSubscriptions(publication.getPubSet());

        VisitModelApi.Model model = ValueMapping.from(VisitModelApi.Model.class, publication, extension);

        model.setRootExtId(extension.getId());

        model.setSubscriptions(subscriptions);

        model.setIntensions(intensions);

        return model;
    }
}
