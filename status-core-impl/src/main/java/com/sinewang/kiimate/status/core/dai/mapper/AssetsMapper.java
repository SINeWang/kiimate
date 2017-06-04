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

    List<AssetDai.Providers> queryProviders(
            @Param("providerId") String providerId);

    List<AssetDai.Assets> queryAssets(
            @Param("providerId") String providerId,
            @Param("group") String group);

    AssetDai.Assets selectAsset(
            @Param("providerId") String providerId,
            @Param("pubSet") long pubSet,
            @Param("stability") String stability,
            @Param("version") String version);

    AssetDai.Assets selectAssetByProviderGroupNameStabilityVersion(
            @Param("providerId") String providerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("stability") String stability,
            @Param("version") String version);

    AssetDai.Assets selectAssetByProviderModelSubIdStabilityVersion(
            @Param("providerId") String providerId,
            @Param("modelSubId") long subId,
            @Param("stability") String stability,
            @Param("version") String version);


    void insert(
            @Param("id") String id,
            @Param("subscriberId") String subscriberId,
            @Param("subSet") String subSet,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    int countById(@Param("id") String id);


}
