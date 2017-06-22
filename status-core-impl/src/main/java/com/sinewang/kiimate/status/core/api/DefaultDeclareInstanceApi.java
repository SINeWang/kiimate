package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.DeclareInstanceApi;
import one.kii.kiimate.status.core.fui.AnModelSubExtractFui;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.InsideView;
import one.kii.summer.zoom.ZoomInByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultDeclareInstanceApi implements DeclareInstanceApi {

    @Autowired
    private AnModelSubExtractFui modelSubExtractFui;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Override
    public Receipt commit(WriteContext context, Form form) throws Conflict, Panic {


        ModelSubscriptionDai.Instance instance = modelSubExtractFui.extract(form, context);

        try {
            modelSubscriptionDai.remember(instance);
        } catch (Conflict ignore) {
            ZoomInByName zoomInByName = ValueMapping.from(ZoomInByName.class, form);

            zoomInByName.setSubscriberId(context.getOwnerId());

            InsideView insideView = modelSubscriptionDai.loadModelSubByName(zoomInByName);

            instance.setId(insideView.getId());
        }

        Receipt receipt = ValueMapping.from(Receipt.class, instance);

        return NotBadResponse.of(receipt);
    }


}
