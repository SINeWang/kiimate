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
    void remember(List<Value> values) throws Conflict;

    List<Value> loadInstances(ZoomInById channel) throws Panic;

    @Data
    class Value {

        private String id;

        private String commit;

        @Commit
        private String ownerId;

        @Commit
        private String subId;

        @Commit
        private String extId;

        @Commit
        private String intId;

        @Commit
        private String field;

        @Commit
        private String[] values;

        @MayHave
        @Commit
        private String valueSet;

        @MayHave
        @Commit
        private String glimpseId;

        @Commit
        private String operatorId;

        @Commit
        private Date beginTime;

        @Commit
        @MayHave
        private Date endTime;

    }

}
