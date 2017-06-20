package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.api.SearchGlimpseIntensionsApi;
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

import java.util.List;

/**
 * Created by WangYanJiong on 20/06/2017.
 */

@Component
public class DefaultSearchGlimpseIntensionsApi implements SearchGlimpseIntensionsApi {

    @Autowired
    private GlimpsesDai glimpsesDai;

    @Autowired
    private ModelSubscriptionDai modelSubscriptionDai;

    @Autowired
    private IntensionDai intensionDai;

    @Override
    public List<Intension> search(ReadContext context, ZoomOutBySet form) throws BadRequest, Panic {

        GlimpsesDai.Glimpse glimpse = glimpsesDai.load(form);

        ModelSubscriptionDai.ClueModelSubId modelSubId = new ModelSubscriptionDai.ClueModelSubId();

        modelSubId.setId(glimpse.getModelSubId());

        ModelSubscriptionDai.Instance instance;
        try {
            instance = modelSubscriptionDai.load(modelSubId);
        } catch (NotFound notFound) {
            throw new Panic(notFound.getKeys());
        }

        IntensionDai.ChannelPubSet set = ValueMapping.from(IntensionDai.ChannelPubSet.class, instance);

        List<IntensionDai.Record> records = intensionDai.loadLast(set);

        List<Intension> intensions = ValueMapping.from(Intension.class, records);

        return NotBadResponse.of(intensions);
    }
}
