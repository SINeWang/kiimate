package com.sinewang.kiimate.subject.core.api;

import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.dai.OwnersDai;
import one.kii.kiimate.subject.core.api.SearchSubjectsApi;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Created by WangYanJiong on 15/05/2017.
 */

@Component
public class DefaultSearchSubjectsApi implements SearchSubjectsApi {

    @Autowired
    private OwnersDai ownersDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private ModelPublicationDai modelPublicationDai;

    @Override
    public List<Subjects> search(ReadContext context, Form form) {
        switch (form.getObjectType()) {
            case EXTENSION:
                switch (form.getAccessType()) {
                    case OWNER:
                        List<OwnersDai.Owners> owners = ownersDai.queryOwners(form.getGroup());
                        return DataTools.copy(owners, Subjects.class);
                }
            case INTENSION:
                switch (form.getAccessType()) {
                    case OWNER:
                        List<OwnersDai.Owners> owners = ownersDai.queryOwners(form.getGroup());
                        return DataTools.copy(owners, Subjects.class);
                }
            case MODEL:
                switch (form.getAccessType()) {
                    case SUBSCRIBER:
                        List<ModelSubscriptionDai.Subscribers> subscribers = modelSubscriptionDai.querySubscriberId(form.getGroup());
                        return DataTools.copy(subscribers, Subjects.class);
                    case PROVIDER:
                        List<ModelPublicationDai.Provider> providers = modelPublicationDai.getProviders(form.getGroup());
                        return DataTools.copy(providers, Subjects.class);
                }
            case INSTANCE:
                switch (form.getAccessType()) {
                    case OWNER:
                        List<ModelSubscriptionDai.Subscribers> subscribers = modelSubscriptionDai.querySubscriberId(form.getGroup());
                        return DataTools.copy(subscribers, Subjects.class);
                }
            case ASSET:
            case STATUS:
                switch (form.getAccessType()) {
                    case OWNER:
                        List<ModelSubscriptionDai.Subscribers> subscribers = modelSubscriptionDai.querySubscriberId(form.getGroup());
                        return DataTools.copy(subscribers, Subjects.class);
                }
        }
        return Collections.emptyList();
    }
}
