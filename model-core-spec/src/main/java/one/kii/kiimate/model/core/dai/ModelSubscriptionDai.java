package one.kii.kiimate.model.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.Unique;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.zoom.InsideView;
import one.kii.summer.zoom.ZoomInById;
import one.kii.summer.zoom.ZoomInByName;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    @Transactional
    void remember(Instance instance) throws Conflict;

    Instance load(ClueModelSubId instance) throws NotFound, Panic;

    InsideView loadModelSubById(ZoomInById channel) throws Panic;

    InsideView loadModelSubByName(ZoomInByName channel) throws Panic;

    List<Instance> querySubscriptions(ClueGroup clue) throws BadRequest, Panic;

    List<Subscribers> querySubscribers(ClueSubscriberId clue) throws Panic;

    Integer countModelSubscriptions(Long pubSet);

    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

    @Data
    class ClueModelSubId {
        Long id;
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
    class Instance {

        private Long id;

        @Unique
        private Long set;

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
