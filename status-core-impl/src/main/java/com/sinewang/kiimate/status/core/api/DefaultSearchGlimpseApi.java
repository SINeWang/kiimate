package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SearchGlimpseApi;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 19/06/2017.
 */
@Component
public class DefaultSearchGlimpseApi implements SearchGlimpseApi {


    @Autowired
    private GlimpsesDai glimpsesDai;

    @Override
    public List<Receipt> search(ReadContext context, Form form) throws BadRequest, Panic {

        GlimpsesDai.ClueGroup clue = new GlimpsesDai.ClueGroup();
        clue.setGroup(form.getGroup());
        clue.setOwnerId(context.getOwnerId());

        List<GlimpsesDai.Glimpse> glimpses = glimpsesDai.queryPublications(clue);
        List<Receipt> list = ValueMapping.from(Receipt.class, glimpses);

        return NotBadResponse.of(list);
    }
}
