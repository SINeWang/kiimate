package one.kii.kiimate.status.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.api.RefreshEntireInstanceApi;
import one.kii.kiimate.status.core.api.RefreshPartialInstanceApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.io.context.WriteContext;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnInstanceExtractor {

    List<InstanceDai.Instance> extract(WriteContext context,
                                       RefreshEntireInstanceApi.SubIdForm form,
                                       MultiValueMap<String, IntensionDai.Record> dict);

    List<InstanceDai.Instance> extract(WriteContext context,
                                       RefreshPartialInstanceApi.SubIdForm form,
                                       MultiValueMap<String, IntensionDai.Record> dict);
}
