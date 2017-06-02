package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.exception.NotFound;

import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
public interface LoadAssetsDai {

    List<Assets> queryAssets(ClueGroup clue);

    Assets loadAssets(ChannelGroupName channel) throws NotFound;

    Assets loadAssets(ChannelPubSet channel) throws NotFound;

    Assets loadAssets(ChannelModelSubId channel) throws NotFound;

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
