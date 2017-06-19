package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.VisitStatusPublicationApi;
import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.ZoomOutBySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 19/6/17.
 */

@Component
public class DefaultVisitStatusPublicationApi implements VisitStatusPublicationApi {


    @Autowired
    private GlimpsesDai glimpsesDai;

    @Override
    public Publication visit(ReadContext context, ZoomOutBySet form) throws NotFound, BadRequest, Panic {

        GlimpsesDai.Publication publication = glimpsesDai.load(form);

        return NotBadResponse.of(
                ValueMapping.from(Publication.class, publication)
        );
    }

}
