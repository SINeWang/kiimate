package one.kii.kiimate.status.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.api.RefreshStatusApi;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.context.WriteContext;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnInstanceExtractor {

    List<Instance> extract(WriteContext context,
                           RefreshStatusApi.SubIdForm form,
                           Map<String, IntensionDai.Record> dict);

    @Data
    class Instance {

        private Long id;

        private String commit;

        @KeyFactor
        private String ownerId;

        @KeyFactor
        private Long subId;

        @KeyFactor
        private Long intId;

        @KeyFactor
        private Long extId;

        @KeyFactor
        private String operatorId;

        @KeyFactor
        private String field;

        @KeyFactor
        private String valueRefPath;

        @KeyFactor
        private String valueRefType;

        @KeyFactor
        private String[] values;

    }
}
