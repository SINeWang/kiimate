package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.AssetsDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Mapper
public interface AssetsMapper {

    void insertAsset(
            @Param("id") long id,
            @Param("pubSet") long pubSet,
            @Param("providerId") String providerId,
            @Param("modelSubId") long subId,
            @Param("insId") long insId,
            @Param("version") String version,
            @Param("visibility") String visibility,
            @Param("stability") String stability,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime
    );

    List<AssetPublicationDai.Providers> queryProviders(@Param("providerId") String providerId);

    List<AssetsDai.Asset> queryAssets(@Param("providerId") String providerId,
                                      @Param("group") String group);

    AssetsDai.Asset selectAsset(@Param("providerId") String providerId,
                                @Param("pubSet") long pubSet,
                                @Param("stability") String stability,
                                @Param("version") String version);

    AssetsDai.Asset selectAssetByProviderGroupNameStabilityVersion(@Param("providerId") String providerId,
                                                                   @Param("group") String group,
                                                                   @Param("name") String name,
                                                                   @Param("stability") String stability,
                                                                   @Param("version") String version);

    AssetsDai.Asset selectAssetByProviderModelSubIdStabilityVersion(@Param("providerId") String providerId,
                                                                    @Param("modelSubId") long subId,
                                                                    @Param("stability") String stability,
                                                                    @Param("version") String version);

    void revokeAsset(@Param("providerId") String providerId,
                     @Param("pubSet") long pubSet,
                     @Param("endTime") Date endTime);
}
