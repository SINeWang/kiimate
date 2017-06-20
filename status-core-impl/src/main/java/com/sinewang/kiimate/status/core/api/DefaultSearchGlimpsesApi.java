package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SearchGlimpsesApi;
import one.kii.kiimate.status.core.api.SearchStatusesApi;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.OutsideView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 19/06/2017.
 */
@Component
public class DefaultSearchGlimpsesApi implements SearchGlimpsesApi {


    @Autowired
    private GlimpsesDai glimpsesDai;

    @Override
    public List<Glimpse> search(ReadContext context, Form form) throws BadRequest, Panic {

        GlimpsesDai.ClueGroup clue = new GlimpsesDai.ClueGroup();
        clue.setGroup(form.getGroup());
        clue.setOwnerId(context.getOwnerId());

        List<OutsideView> glimpses = glimpsesDai.queryGlimpses(clue);

        List<Glimpse> list = ValueMapping.from(Glimpse.class, glimpses);

        return NotBadResponse.of(list);
    }
}
