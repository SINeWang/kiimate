package one.kii.kiimate.model.core.fui;

import one.kii.kiimate.model.core.api.PublishModelApi;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.Panic;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnPublicationExtractor {

    List<ModelPublicationDai.Record> extract(
            WriteContext context,
            PublishModelApi.Form form,
            List<IntensionDai.Record> records,
            ModelPublicationDai.ChannelSet pubSet) throws Panic;


}
