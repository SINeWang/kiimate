package com.sinewang.kiimate.model.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.derid.derid64.Eid64Generator;
import one.kii.kiimate.model.core.api.SubscribeModelsApi;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.model.core.fui.AnStatusExtractor;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultStatusExtrator implements AnStatusExtractor {

    private static final Eid64Generator idgen = new Eid64Generator(6);

    @Override
    public ModelSubscriptionDai.Status extract(SubscribeModelsApi.Form form, WriteContext context) throws Panic {
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));

        ModelSubscriptionDai.Status status = ValueMapping.from(ModelSubscriptionDai.Status.class, form);
        status.setSubscriberId(context.getOwnerId());
        status.setOperatorId(context.getOperatorId());

        status.setId(idgen.born());
        return NotBadResponse.of(status);
    }
}
