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
    public List<Models> search(ReadContext context, QueryModelsForm form) {
        List<ModelPublicationDai.PublishedExtension> extensions = modelPublicationDai.queryPublicationsByGroup(form.getQuery());
        List<Models> models = new ArrayList<>();
        for (ModelPublicationDai.PublishedExtension extension : extensions) {
            ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, extension);
            ExtensionDai.Extension lastExtension;
            try {
                lastExtension = extensionDai.loadLastExtension(channel);
            } catch (NotFound notFound) {
                continue;
            }
            List<Snapshot> snapshots = new ArrayList<>();

            List<ModelPublicationDai.PublishedSnapshot> snapshotList = modelPublicationDai.queryPublishedSnapshotsByExtId(extension.getId());

            Models model = ValueMapping.from(Models.class, lastExtension, extension);
            for (ModelPublicationDai.PublishedSnapshot publishedSnapshot : snapshotList) {
                int subscriptions = modelSubscriptionDai.countModelSubscriptions(publishedSnapshot.getPubSet());


                Snapshot snapshot = ValueMapping.from(Snapshot.class, publishedSnapshot);
                snapshot.setSubscriptions(subscriptions);
                snapshots.add(snapshot);

                model.setSnapshots(snapshots);

            }
            models.add(model);
        }
        return models;
    }
}
