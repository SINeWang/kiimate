package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SearchModelsApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */
@Component
public class DefaultSearchModelsApi implements SearchModelsApi {


    @Autowired
    private ModelPublicationDai modelPublicationDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private ExtensionDai extensionDai;

    @Autowired
    private IntensionDai intensionDai;

    @Override
    public List<Provider> search(ReadContext context, QueryProvidersForm form) {
        List<ModelPublicationDai.Provider> providerList = modelPublicationDai.getProviders(form.getQuery());
        return ValueMapping.from(Provider.class, providerList);
    }

    @Override
    public List<Model> search(ReadContext context, QueryModelsForm form) {
        List<ModelPublicationDai.Publication> publications = modelPublicationDai.queryPublicationsByGroup(form.getQuery());
        List<Model> models = new ArrayList<>();
        for (ModelPublicationDai.Publication publication : publications) {
            ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, publication);
            channel.setId(publication.getExtId());
            ExtensionDai.Extension extension;
            try {
                extension = extensionDai.loadLastExtension(channel);
            } catch (NotFound notFound) {
                continue;
            }

            IntensionDai.ChannelPubSet pubSet = ValueMapping.from(IntensionDai.ChannelPubSet.class, publication, extension);


            List<IntensionDai.Intension> intensionList = intensionDai.loadLastIntensions(pubSet);
            List<Intension> intensions = ValueMapping.from(Intension.class, intensionList);


            int subscriptions = modelSubscriptionDai.countModelSubscriptions(publication.getPubSet());

            Model model = ValueMapping.from(Model.class, publication, extension);

            model.setRootExtId(extension.getId());

            model.setIntensions(intensions);

            model.setSubscriptions(subscriptions);

            models.add(model);
        }
        return models;
    }
}
