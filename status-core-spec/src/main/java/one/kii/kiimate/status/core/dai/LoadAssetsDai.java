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

    Assets fetchAssets(ChannelGroupName channel) throws NotFound;

    Assets fetchAssets(ChannelPubSet channel) throws NotFound;

    Assets fetchAssets(ChannelModelSubId channel) throws NotFound;

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
        String pubSet;

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
        String modelSubId;

        @KeyFactor
        String stability;

        @KeyFactor
        String version;
    }

    @Data
    class Assets {

        String pubSet;

        String providerId;

        String visibility;

        String modelSubId;

        String group;

        String name;

        String stability;

        String version;
    }

}
