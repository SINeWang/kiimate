package one.kii.kiimate.model.core.fui;


import one.kii.kiimate.model.core.dai.IntensionDai;

import java.util.Map;

/**
 * Created by WangYanJiong on 08/04/2017.
 */
public interface AnModelRestorer {

    Map<String, Object> restoreAsMetaData(IntensionDai.ChannelLastExtension channel);

    Map<String, Object> restoreAsMetaData(IntensionDai.ChannelLatestExtension channel);

    Map<String, IntensionDai.Record> restoreAsIntensionDict(IntensionDai.ChannelLastExtension channel);
}
