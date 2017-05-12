package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.stereotype.Component;
import one.kii.kiimate.model.core.api.SubscribeModelApi;
import one.kii.kiimate.model.core.fui.AnSubscribeModelExtractor;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultSubscribeModelExtrator implements AnSubscribeModelExtractor {


    @Override
    public String hashId(String subscriberId, String pubSetHash, String group, String name, String tree) {
        return HashTools.hashHex(subscriberId, pubSetHash, group, name, tree);
    }

    @Override
    public ModelSubscription extract(SubscribeModelApi.Form form, String subscriberId, String operatorId, String tree) {
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));

        ModelSubscription subscription = new ModelSubscription();
        subscription.setSubSet(form.getPubSet());
        subscription.setGroup(form.getGroup());
        subscription.setName(form.getName());
        subscription.setSubscriberId(subscriberId);
        subscription.setTree(TREE_MASTER);
        subscription.setOperatorId(operatorId);

        String id = hashId(subscriberId, form.getPubSet(), form.getGroup(), form.getName(), tree);

        subscription.setId(id);
        return subscription;
    }
}
