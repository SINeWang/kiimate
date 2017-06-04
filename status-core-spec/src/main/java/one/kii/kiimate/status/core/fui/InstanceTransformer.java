package one.kii.kiimate.status.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */
public interface InstanceTransformer {

    Map<String, Object> toTimedValue(List<InstanceDai.Instance> instancesList, ModelSubscriptionDai.ModelPubSet model) throws Panic, BadRequest;

    Map<String, Object> toRawValue(List<InstanceDai.Instance> instancesList, ModelSubscriptionDai.ModelPubSet pubSet) throws Panic, BadRequest;

    @Data
    class TimedValue {
        Object value;
        Date time;
    }

}
