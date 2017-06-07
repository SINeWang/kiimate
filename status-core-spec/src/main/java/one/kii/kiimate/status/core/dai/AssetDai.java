package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.xyz.VisitUpWithId;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
public interface AssetDai {

    List<Providers> queryProviders(ClueId clue) throws BadRequest;

    Assets load(VisitUpWithId channel) throws Panic;

    @Transactional
    void remember(Subscription subscription) throws BadRequest;

    @Transactional
    void remember(Publication publication, List<Entry> entries);

    @Data
    class Publication {

        Long pubSet;

        String providerId;

        Long modelSubId;

        String operatorId;

        String version;

        String visibility;

        String stability;

        Date beginTime;

        @MayHave
        Date endTime;
    }

    @Data
    class Entry {

        Long id;

        Long insId;
    }


    @Data
    class Subscription {
        Long id;

        String subscriberId;

        String subSet;

        String group;

        String name;

        String tree;

        String operatorId;

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


        String ownerId;


        String group;


        String name;


        String stability;


        String version;
    }

    @Data
    class Assets {

        Long id;

        String subscriberId;

        String providerId;

        String set;

        String version;

        Date beginTime;

        @MayHave
        Date endTime;
    }

}
