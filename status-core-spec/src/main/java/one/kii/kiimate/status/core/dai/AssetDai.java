package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.exception.NotFound;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
public interface AssetDai {

    List<Providers> queryProviders(ClueId clue);

    List<Asset> query(ClueGroup clue);

    Asset load(ChannelGroupName channel) throws NotFound;

    Asset load(ChannelPubSet channel) throws NotFound;

    Asset load(ChannelModelSubId channel) throws NotFound;

    @Transactional
    void save(Record record);

    @Data
    class Record {
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
    class Asset {

        Long pubSet;

        String providerId;

        String visibility;

        Long subId;

        String group;

        String name;

        String stability;

        String version;
    }

}
