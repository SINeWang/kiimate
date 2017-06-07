package one.kii.kiimate.model.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.Unique;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.VisitUpInsight;
import one.kii.summer.xyz.VisitUpWithId;
import one.kii.summer.xyz.VisitUpWithXyz;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    @Transactional
    void remember(Status status) throws Conflict;

    VisitUpInsight getModelPubSetByStatusId(VisitUpWithId channel) throws Panic;

    VisitUpInsight getModelPubSetByXyz(VisitUpWithXyz channel) throws Panic;

    List<Status> querySubscriptions(ClueGroup clue) throws BadRequest, Panic;

    List<Subscribers> querySubscribers(ClueSubscriberId clue) throws Panic;

    Status selectSubscription(VisitUpWithId channel) throws Panic, BadRequest;

    Integer countModelSubscriptions(Long pubSet);

    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

    @Data
    class ClueSubscriberId {
        String id;
    }


    @Data
    class Subscribers {
        String id;
    }


    @Data
    class Status {

        private Long id;

        @Unique
        private Long subSet;

        @Unique
        private String subscriberId;

        @Unique
        private String group;

        @Unique
        private String name;

        @Unique
        private String tree;

        private String operatorId;

        private Date beginTime;

        @Unique
        @MayHave
        private Date endTime;

    }

}
