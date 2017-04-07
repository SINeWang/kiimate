package com.sinewang.metamate.core.fi;

import one.kii.summer.codec.utils.HashTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;
import wang.yanjiong.metamate.core.fi.AnPublicationExtractor;
import wang.yanjiong.metamate.core.fi.AnSubscribeModelExtractor;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultSubscribeModelExtrator implements AnSubscribeModelExtractor {

    @Autowired
    private AnPublicationExtractor publicationExtractor;

    @Override
    public String hashId(String pubExtId, String subscriberId) {
        return HashTools.hashHex(pubExtId, subscriberId);
    }

    @Override
    public ModelSubscription extract(SubscribeModelApi.Form form, String providerId, String extId, String publication, String version, String subscriberId, String operatorId) {

        ModelSubscription subscription = new ModelSubscription();

        subscription.setExtId(extId);
        subscription.setGroup(form.getGroup());
        subscription.setName(form.getName());
        subscription.setTree(form.getTree());

        subscription.setSubscriberId(subscriberId);
        subscription.setProviderId(providerId);
        subscription.setPublication(publication);
        subscription.setVersion(version);

        subscription.setOperatorId(operatorId);

        String pubExtId = publicationExtractor.hashPubExtId(providerId, extId, publication, version);

        String id = hashId(pubExtId, subscriberId);

        subscription.setId(id);
        return subscription;
    }
}
