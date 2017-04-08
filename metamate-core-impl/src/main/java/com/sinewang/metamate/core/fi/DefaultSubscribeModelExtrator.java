package com.sinewang.metamate.core.fi;

import com.google.common.base.CaseFormat;
import one.kii.summer.codec.utils.HashTools;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.api.SubscribeModelApi;
import wang.yanjiong.metamate.core.fi.AnSubscribeModelExtractor;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultSubscribeModelExtrator implements AnSubscribeModelExtractor {


    @Override
    public String hashId(String pubExtId, String subscriberId) {
        return HashTools.hashHex(pubExtId, subscriberId);
    }

    @Override
    public ModelSubscription extract(SubscribeModelApi.Form form, String pubSetHash, String pubExtId, String subscriberId, String operatorId) {
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));

        ModelSubscription subscription = new ModelSubscription();
        subscription.setPubSetHash(pubSetHash);
        subscription.setGroup(form.getGroup());
        subscription.setName(NAME_ROOT);
        subscription.setTree(TREE_MASTER);
        subscription.setPubExtId(pubExtId);
        subscription.setSubscriberId(subscriberId);
        subscription.setOperatorId(operatorId);

        String id = hashId(pubExtId, subscriberId);

        subscription.setId(id);
        return subscription;
    }
}
