package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.SearchInstancesApi;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 10/05/2017.
 */

@Component
public class DefaultSearchInstancesApi implements SearchInstancesApi {

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public List<Instance> search(ReadContext context, QueryForm form) throws BadRequest, Panic {
        ModelSubscriptionDai.ClueGroup clue = ValueMapping.from(ModelSubscriptionDai.ClueGroup.class, context, form);
        List<ModelSubscriptionDai.Instance> list = modelSubscriptionDai.querySubscriptions(clue);
        return ValueMapping.from(Instance.class, list);
    }
}
