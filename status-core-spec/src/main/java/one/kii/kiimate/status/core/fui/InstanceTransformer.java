package one.kii.kiimate.status.core.fui;

import one.kii.kiimate.status.core.dai.InstanceDai;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */
public interface InstanceTransformer {

    Map<String, Object> from(List<InstanceDai.Instance> instancesList, String rootExtId);
}
