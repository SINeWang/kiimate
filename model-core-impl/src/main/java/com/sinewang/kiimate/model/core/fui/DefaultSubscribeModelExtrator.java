package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.kiimate.model.core.fui.AnSubscribeModelExtractor;
import one.kii.summer.beans.utils.HashTools;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultSubscribeModelExtrator implements AnSubscribeModelExtractor {

    @Override
    public ModelSubscription extract(SubscribeModelsApi.Form form, WriteContext context) {
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));

        ModelSubscription subscription = ValueMapping.from(ModelSubscription.class, form);
        subscription.setSubscriberId(context.getOwnerId());
        subscription.setOperatorId(context.getOperatorId());

        String id = HashTools.hashHex(subscription);

        subscription.setId(id);
        return subscription;
    }
}
