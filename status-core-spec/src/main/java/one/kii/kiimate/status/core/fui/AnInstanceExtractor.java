package one.kii.kiimate.status.core.fui;

import lombok.Data;

import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.io.context.WriteContext;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnInstanceExtractor {

    List<Instance> extract(WriteContext context, String subId, Map<String, List<String>> values, Map<String, IntensionDai.Intension> dict);

    @Data
    class Instance {

        private String id;

        private String ownerId;

        private String subId;

        private String intId;

        private String extId;

        private String operatorId;

        private String field;

        private String[] values;

    }
}
