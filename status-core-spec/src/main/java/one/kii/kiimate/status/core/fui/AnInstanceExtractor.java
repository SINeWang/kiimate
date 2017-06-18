package one.kii.kiimate.status.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.api.RefreshInstanceApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.io.context.WriteContext;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnInstanceExtractor {

    List<InstanceDai.Instance> extract(WriteContext context,
                                       RefreshInstanceApi.SubIdForm form,
                                       Map<String, IntensionDai.Record> dict);


}
