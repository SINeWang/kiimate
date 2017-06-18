package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SearchModelsApi;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
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

    @Override
    public List<Models> search(ReadContext context, QueryModelsForm form) throws BadRequest, Panic {
        ModelPublicationDai.ClueGroup group = ValueMapping.from(ModelPublicationDai.ClueGroup.class, form);

        List<ModelPublicationDai.PublishedExtension> extensions = modelPublicationDai.searchExtension(group);
        List<Models> models = new ArrayList<>();
        for (ModelPublicationDai.PublishedExtension extension : extensions) {
            ExtensionDai.ChannelId channel = ValueMapping.from(ExtensionDai.ChannelId.class, extension);
            ExtensionDai.Record lastRecord;
            try {
                lastRecord = extensionDai.loadLast(channel);
            } catch (NotFound notFound) {
                return Collections.emptyList();
            }
            List<Snapshot> snapshots = new ArrayList<>();

            ModelPublicationDai.ChannelId id = ValueMapping.from(ModelPublicationDai.ChannelId.class, extension);

            List<ModelPublicationDai.PublishedSnapshot> snapshotList = modelPublicationDai.loadSnapshot(id);

            Models model = ValueMapping.from(Models.class, lastRecord, extension);
            for (ModelPublicationDai.PublishedSnapshot publishedSnapshot : snapshotList) {
                int subscriptions = modelSubscriptionDai.countModelSubscriptions(publishedSnapshot.getSet());


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
