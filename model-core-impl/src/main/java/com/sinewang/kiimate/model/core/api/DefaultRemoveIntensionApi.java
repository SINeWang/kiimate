package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.RemoveIntensionApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.fui.AnModelRestorer;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 09/05/2017.
 */
@Component
public class DefaultRemoveIntensionApi implements RemoveIntensionApi {

    @Autowired
    private IntensionDai intensionDai;

    @Autowired
    private AnModelRestorer modelRestorer;

    @Override
    public Receipt commit(WriteContext context, Form form) throws Conflict, NotFound {
        IntensionDai.ChannelId channel = ValueMapping.from(IntensionDai.ChannelId.class, form);
        intensionDai.forget(channel);

        IntensionDai.ChannelLatestExtension latest = new IntensionDai.ChannelLatestExtension();
        latest.setId(form.getExtId());
        List<Intension> intensions = ValueMapping.from(Intension.class, intensionDai.load(latest));
        Map<String, Object> schema = modelRestorer.restoreAsMetaData(latest);
        Receipt receipt = new Receipt();
        receipt.setIntensions(intensions);
        receipt.setSchema(schema);
        return receipt;
    }
}
