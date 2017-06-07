package one.kii.kiimate.model.core.fui;


import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;

import java.util.Map;

/**
 * Created by WangYanJiong on 08/04/2017.
 */
public interface AnModelRestorer {

    Map<String, Object> restoreAsMetaData(IntensionDai.ChannelExtensionId channel) throws BadRequest, NotFound, Panic;

    Map<String, IntensionDai.Record> restoreAsIntensionDict(IntensionDai.ChannelExtensionId channel) throws NotFound, BadRequest, Panic;
}
