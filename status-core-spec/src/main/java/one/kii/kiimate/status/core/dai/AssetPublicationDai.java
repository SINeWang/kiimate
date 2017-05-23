package one.kii.kiimate.status.core.dai;

import lombok.Data;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.exception.NotFound;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 19/05/2017.
 */
public interface AssetPublicationDai {

    @Transactional
    Date insert(String pubSet, List<Record> records);

    List<Providers> queryProviders(String providerId);

    List<Assets> queryAssets(String ownerId, String group);

    Assets selectAssets(String ownerId, String group, String name, String stability, String version) throws NotFound;

    Assets selectAssets(String ownerId, String pubSet, String stability, String version);

    @Data
    class Record {
        String id;
        String providerId;
        String modelSubId;
        String insId;
        String version;
        String visibility;
        String stability;
    }

    @Data
    class Providers {
        String id;
    }

    @Data
    class Assets {

        String pubSet;

        String providerId;

        String visibility;

        String modelSubId;

        @KeyFactor
        String group;
        @KeyFactor
        String name;
        @KeyFactor
        String stability;
        @KeyFactor
        String version;
    }
}
