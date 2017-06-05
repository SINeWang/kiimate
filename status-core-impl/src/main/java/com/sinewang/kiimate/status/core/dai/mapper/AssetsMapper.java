package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.kiimate.status.core.dai.AssetDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Mapper
public interface AssetsMapper {

    AssetDai.Publication selectPublication(
            @Param("id") Long id
    );

    List<AssetDai.Providers> queryProviders(
            @Param("providerId") String providerId
    );

    List<AssetDai.Assets> queryAssets(
            @Param("providerId") String providerId,
            @Param("group") String group);

    AssetDai.Assets selectAsset(
            @Param("subscriberId") String subscriberId,
            @Param("id") Long id);

    AssetDai.Assets selectAssetByProviderGroupNameStabilityVersion(
            @Param("providerId") String providerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("stability") String stability,
            @Param("version") String version);

    void insertSubscription(
            @Param("id") String id,
            @Param("subscriberId") String subscriberId,
            @Param("subSet") String subSet,
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

    int countById(@Param("id") String id);

}
