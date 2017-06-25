package one.kii.kiimate.status.core.fui;

import lombok.Data;
import one.kii.kiimate.status.core.dai.InstanceDai;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.InsideView;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 21/05/2017.
 */
public interface InstanceTransformer {

    Map<String, Object> toFatValue(List<InstanceDai.Value> values, InsideView model) throws Panic, BadRequest;

    Map<String, Object> toRawValue(List<InstanceDai.Value> values, InsideView pubSet) throws Panic, BadRequest;

    @Data
    class FatValue {
        Object value;
        Date time;
        String glimpseId;
    }

}
