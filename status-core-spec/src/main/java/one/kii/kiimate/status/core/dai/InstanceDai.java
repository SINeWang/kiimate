package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.Commit;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.ZoomInById;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface InstanceDai {

    @Transactional
    void remember(List<Instance> instances) throws Conflict;

    List<Record> loadInstances(ZoomInById channel) throws Panic;

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

        @MayHave
        private Long valueSet;

        @MayHave
        private Long valueRefId;

        @MayHave
        private String valueRefPolicy;

        private String operatorId;

        private Date beginTime;

        @MayHave
        private Date endTime;

    }

    @Data
    class Instance {

        private Long id;

        private String commit;

        @Commit
        private String ownerId;

        @Commit
        private Long subId;

        @Commit
        private Long extId;

        @Commit
        private Long intId;

        @Commit
        private String field;

        @Commit
        private String[] values;

        @Commit
        private Long valueSet;

        @Commit
        private String valueRefPolicy;

        @Commit
        private String valueRefId;

        @Commit
        private String operatorId;

        @Commit
        private Date beginTime;

        @Commit
        @MayHave
        private Date endTime;

    }

}
