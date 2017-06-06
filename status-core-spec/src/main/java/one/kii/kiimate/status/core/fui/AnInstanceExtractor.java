package one.kii.kiimate.status.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.kiimate.status.core.api.RefreshStatusApi;
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

        private String ownerId;

        private Long subId;


        private Long intId;


        private Long extId;


        private String operatorId;


        private String field;


        private String valueRefPath;


        private String valueRefType;


        private String[] values;

    }
}
