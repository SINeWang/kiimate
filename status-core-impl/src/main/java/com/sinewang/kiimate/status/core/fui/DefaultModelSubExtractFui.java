package com.sinewang.kiimate.status.core.fui;

import com.google.common.base.CaseFormat;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.DeclareInstanceApi;
import one.kii.kiimate.status.core.fui.AnModelSubExtractFui;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.txdid.txd64.T1Did64Generator;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultModelSubExtractFui implements AnModelSubExtractFui {

    private static final T1Did64Generator idgen = new T1Did64Generator(6);

    @Override
    public ModelSubscriptionDai.Instance extract(DeclareInstanceApi.Form form, WriteContext context) throws Panic {
        form.setGroup(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN, form.getGroup()));

        ModelSubscriptionDai.Instance instance = ValueMapping.from(ModelSubscriptionDai.Instance.class, form);
        instance.setSubscriberId(context.getOwnerId());
        instance.setOperatorId(context.getOperatorId());

        instance.setId(String.valueOf(idgen.born()));
        instance.setBeginTime(new Date());
        return NotBadResponse.of(instance);
    }
}
