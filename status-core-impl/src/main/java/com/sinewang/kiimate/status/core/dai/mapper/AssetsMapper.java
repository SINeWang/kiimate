package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.kiimate.status.core.dai.AssetDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Mapper
public interface AssetsMapper {

    List<AssetDai.Providers> queryProviders(
            @Param("providerId") String providerId
    );

    List<AssetDai.Subscribers> querySubscribers(
            @Param("subscriberId") String subscriberId
    );

    AssetDai.Assets selectAsset(
            @Param("subscriberId") String subscriberId,
            @Param("id") Long id);

    void insertSubscription(
            @Param("id") Long id,
            @Param("subscriberId") String subscriberId,
            @Param("subSet") String subSet,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void insertPublication(
            @Param("id") Long id,
            @Param("pubSet") Long pubSet,
            @Param("providerId") String providerId,
            @Param("modelSubId") Long modelSubId,
            @Param("insId") Long insId,
            @Param("version") String version,
            @Param("stability") String stability,
            @Param("visibility") String visibility,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    int countByConflictKey(Map<String, Object> map);

}
