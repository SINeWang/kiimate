package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
public interface AssetDai {

    List<Providers> queryProviders(ClueId clue);

    List<Assets> query(ClueGroup clue) throws BadRequest;

    Assets load(ChannelGroupName channel) throws Panic;

    Assets load(ChannelPubSet channel) throws Panic;

    Assets load(ChannelModelSubId channel) throws Panic;

    @Transactional
    void remember(Subscription subscription);

    @Transactional
    Date remember(Publication publication);

    @Data
    class Publication {

        Long pubSet;

        List<StatusDai.Entry> entries;

        String operatorId;
    }

    @Data
    class Subscription {
        String id;

        @KeyFactor
        String subscriberId;

        @KeyFactor
        String subSet;

        String operatorId;

        @KeyFactor
        Date beginTime;
    }

    @Data
    class Subscribers {
        String id;
    }

    @Data
    class ClueId {
        String id;
    }

    @Data
    class Providers {
        String id;
    }


    @Data
    class ClueGroup {
        String ownerId;
        String group;
    }

    @Data
    class ChannelGroupName {

        @KeyFactor
        String ownerId;

        @KeyFactor
        String group;

        @KeyFactor
        String name;

        @KeyFactor
        String stability;

        @KeyFactor
        String version;
    }

    @Data
    class ChannelPubSet {

        @KeyFactor
        String ownerId;

        @KeyFactor
        Long pubSet;

        @KeyFactor
        String stability;

        @KeyFactor
        String version;
    }

    @Data
    class ChannelModelSubId {

        @KeyFactor
        String ownerId;

        @KeyFactor
        Long subId;

        @KeyFactor
        String stability;

        @KeyFactor
        String version;
    }

    @Data
    class Assets {

        Long id;

        String ownerId;

        String group;

        String name;

        String stability;

        String version;

        Date beginTime;

        @MayHave
        Date endTime;
    }

}
