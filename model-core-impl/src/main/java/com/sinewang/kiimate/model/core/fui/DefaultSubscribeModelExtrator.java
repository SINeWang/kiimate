package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.kiimate.model.core.fui.AnSubscribeModelExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultSubscribeModelExtrator implements AnSubscribeModelExtractor {

    private static final Eid64Generator idgen = new Eid64Generator(6);

    @Override
    public ModelSubscription extract(SubscribeModelsApi.Form form, WriteContext context) {
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));

        ModelSubscription subscription = ValueMapping.from(ModelSubscription.class, form);
        subscription.setSubscriberId(context.getOwnerId());
        subscription.setOperatorId(context.getOperatorId());

        subscription.setId(idgen.born());
        return subscription;
    }
}
