package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.xyz.VisitUpWithId;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface InstanceDai {

    @Transactional
    void remember(List<Instance> instances) throws Conflict;

    List<Record> loadInstances(ChannelAssetId channel);

    List<Record> loadInstances(VisitUpWithId channel);


    @Data
    class ChannelAssetId {
        Long id;
    }

    @Data
    class Record {

        private Long id;

        private String commit;

        private String ownerId;

        private Long subId;

        private Long extId;

        private Long intId;

        private String field;

        private String value;

        private Long valueSet;

        private String valueRefId;

        private String valueRefPolicy;

        private String operatorId;

        private Date beginTime;

    }

    @Data
    class Instance {

        private Long id;

        private String commit;

        private String ownerId;

        private Long subId;

        private Long extId;

        private Long intId;

        private String field;

        private String[] values;

        private Long valueSet;

        private String valueRefPolicy;

        private String valueRefId;

        private String operatorId;

        private Date beginTime;

        @MayHave
        private Date endTime;

    }

}
