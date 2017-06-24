package one.kii.kiimate.status.core.fui;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.api.RefreshEntireValueApi;
import one.kii.kiimate.status.core.api.RefreshPartialValueApi;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.io.context.WriteContext;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnInstanceExtractor {

    List<InstanceDai.Value> extract(WriteContext context,
                                    RefreshEntireValueApi.SubIdForm form,
                                    MultiValueMap<String, IntensionDai.Record> dict);

    List<InstanceDai.Value> extract(WriteContext context,
                                    RefreshPartialValueApi.SubIdForm form,
                                    MultiValueMap<String, IntensionDai.Record> dict);
}
