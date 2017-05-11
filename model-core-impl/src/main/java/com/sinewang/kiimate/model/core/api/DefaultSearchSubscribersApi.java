package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SearchSubscribersApi;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 11/05/2017.
 */
@Component
public class DefaultSearchSubscribersApi implements SearchSubscribersApi {

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public List<Subscribers> search(ReadContext context, QueryForm form) {
        List<ModelSubscriptionDai.Subscribers> list = modelSubscriptionDai.querySubscriberId(form.getSubscriberId());
        return DataTools.copy(list, Subscribers.class);
    }
}
