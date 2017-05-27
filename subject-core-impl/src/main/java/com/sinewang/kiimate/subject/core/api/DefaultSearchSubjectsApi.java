package com.sinewang.kiimate.subject.core.api;

import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.dai.OwnersDai;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.subject.core.api.SearchSubjectsApi;
import one.kii.summer.beans.utils.ValueMapping;
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

    @Autowired
    private AssetPublicationDai assetPublicationDai;

    @Override
    public List<Subjects> search(ReadContext context, Form form) {
        switch (form.getObjectType()) {
            case EXTENSION:
                switch (form.getAccessType()) {
                    case OWNER:
                        List<OwnersDai.Owners> subjects = ownersDai.queryOwners(form.getGroup());
                        return ValueMapping.from(Subjects.class, subjects);
                }
            case INTENSION:
                switch (form.getAccessType()) {
                    case OWNER:
                        List<OwnersDai.Owners> subjects = ownersDai.queryOwners(form.getGroup());
                        return ValueMapping.from(Subjects.class, subjects);
                }
            case MODEL:
                switch (form.getAccessType()) {
                    case SUBSCRIBER:
                        List<ModelSubscriptionDai.Subscribers> subjects = modelSubscriptionDai.querySubscriberId(form.getGroup());
                        return ValueMapping.from(Subjects.class, subjects);
                    case PROVIDER:
                        List<ModelPublicationDai.Provider> subjects1 = modelPublicationDai.getProviders(form.getGroup());
                        return ValueMapping.from(Subjects.class, subjects1);
                }
            case INSTANCE:
                switch (form.getAccessType()) {
                    case OWNER:
                        List<ModelSubscriptionDai.Subscribers> subjects = modelSubscriptionDai.querySubscriberId(form.getGroup());
                        return ValueMapping.from(Subjects.class, subjects);
                }
            case ASSET:
                switch (form.getAccessType()) {
                    case OWNER:
                        AssetPublicationDai.ClueId clue = new AssetPublicationDai.ClueId();
                        clue.setId(context.getOwnerId());
                        List<AssetPublicationDai.Providers> subjects = assetPublicationDai.queryProviders(clue);
                        return ValueMapping.from(Subjects.class, subjects);
                }
            case STATUS:
                switch (form.getAccessType()) {
                    case OWNER:
                        List<ModelSubscriptionDai.Subscribers> subjects = modelSubscriptionDai.querySubscriberId(form.getGroup());
                        return ValueMapping.from(Subjects.class, subjects);
                }
        }
        return Collections.emptyList();
    }
}
