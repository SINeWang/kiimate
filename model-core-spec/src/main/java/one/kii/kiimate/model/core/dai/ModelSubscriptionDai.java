package one.kii.kiimate.model.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.Unique;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    @Transactional
    void remember(Status status) throws Conflict;

    ModelPubSet getModelPubSetByStatusId(StatusId channel) throws Panic;

    List<Status> querySubscriptions(ClueGroup clue) throws BadRequest;

    List<Subscribers> querySubscribers(ClueSubscriberId clue);

    Status selectSubscription(ChannelGroupNameTree channel) throws Panic, BadRequest;

    Status selectSubscription(StatusId channel) throws Panic, BadRequest;

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
    class ChannelGroupNameTree {


        String ownerId;

        String group;

        String name;

        String tree;
    }

    @Data
    class StatusId {
        Long id;
    }

    @Data
    class Subscribers {
        String id;
    }

    @Data
    class ModelPubSet {
        Long pubSet;
        Long rootExtId;
        Date beginTime;
        @MayHave
        Date endTime;
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
