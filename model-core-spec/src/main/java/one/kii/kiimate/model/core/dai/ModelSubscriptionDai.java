package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.beans.annotations.KeyFactor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */
public interface ModelSubscriptionDai {


    @Transactional
    void remember(ModelSubscription modelSubscription) throws DuplicatedSubscription;

    ModelPubSet getModelPubSetByOwnerSubscription(ChannelSubId channel);

    List<ModelSubscription> querySubscriptions(ClueGroup clue);

    List<Subscribers> querySubscribers(ClueSubscriberId clue);

    ModelSubscription selectSubscription(ChannelGroupNameTree channel);

    ModelSubscription selectSubscription(ChannelSubId channel);

    int countModelSubscriptions(long pubSet);

    @Data
    class ClueGroup{
        String ownerId;
        String group;
    }

    @Data
    class ClueSubscriberId{
        String id;
    }

    @Data
    class ChannelGroupNameTree{
        String ownerId;
        String group;
        String name;
        String tree;
    }

    @Data
    class ChannelSubId {
        @KeyFactor
        String ownerId;

        @KeyFactor
        long subId;
    }

    @Data
    class Subscribers {
        String id;
    }

    @Data
    class ModelPubSet {
        long pubSet;
        long rootExtId;
        Date beginTime;
        Date endTime;
    }

    @Data
    class ModelSubscription {
        private long id;

        private long subSet;

        @KeyFactor
        private String subscriberId;

        @KeyFactor
        private String group;

        @KeyFactor
        private String name;

        @KeyFactor
        private String tree;

        private String operatorId;

    }

    class DuplicatedSubscription extends Exception {

        @Getter
        private long subSet;

        @Getter
        private String subscriberId;

        @Getter
        private String group;

        @Getter
        private String name;

        @Getter
        private String tree;

        public DuplicatedSubscription(long subSet, String subscriberId, String group, String name, String tree) {
            super();
            this.subSet = subSet;
            this.subscriberId = subscriberId;
            this.group = group;
            this.name = name;
            this.tree = tree;
        }

    }
}
