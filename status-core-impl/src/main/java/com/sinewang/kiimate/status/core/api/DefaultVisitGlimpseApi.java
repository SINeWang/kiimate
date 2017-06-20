package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.VisitGlimpseApi;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.kiimate.status.core.dai.StatusDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutByName;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 19/6/17.
 */

@Component
public class DefaultVisitGlimpseApi implements VisitGlimpseApi {


    @Autowired
    private GlimpsesDai glimpsesDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private StatusDai statusDai;

    @Override
    public Glimpse visit(ReadContext context, ZoomOutBySet form) throws NotFound, BadRequest, Panic {

        GlimpsesDai.Publication publication = glimpsesDai.load(form);

        ZoomOutByName statusName = ValueMapping.from(ZoomOutByName.class, publication);

        ModelSubscriptionDai.ClueModelSubId id = new ModelSubscriptionDai.ClueModelSubId();
        id.setId(publication.getModelSubId());

        ModelSubscriptionDai.Instance instance = modelSubscriptionDai.load(id);

        ZoomOutBySet modelPubSet = new ZoomOutBySet();
        modelPubSet.setSet(instance.getSet());
        modelPubSet.setProviderId(publication.getProviderId());

        OutsideView model = modelSubscriptionDai.selectModelBySet(modelPubSet);

        OutsideView status = statusDai.loadDownstream(statusName);

        Glimpse glimpse1 = new Glimpse();
        Outside model1 = ValueMapping.from(Outside.class, model);
        Outside status1 = ValueMapping.from(Outside.class, status);
        glimpse1.setModel(model1);
        glimpse1.setStatus(status1);
        return NotBadResponse.of(glimpse1);
    }

}
