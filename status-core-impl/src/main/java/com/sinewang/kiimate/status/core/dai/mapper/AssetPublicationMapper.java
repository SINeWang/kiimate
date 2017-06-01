package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.LoadAssetsDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Mapper
public interface AssetPublicationMapper {

    void insertAssetPublication(
            @Param("id") long id,
            @Param("pubSet") long pubSet,
            @Param("providerId") String providerId,
            @Param("modelSubId") long subId,
            @Param("insId") long insId,
            @Param("version") String version,
            @Param("visibility") String visibility,
            @Param("stability") String stability,
            @Param("beginTime") Date beginTime
    );

    List<AssetPublicationDai.Providers> queryProviders(@Param("providerId") String providerId);

    List<LoadAssetsDai.Assets> queryAssets(@Param("providerId") String providerId,
                                           @Param("group") String group);

    LoadAssetsDai.Assets selectAsset(@Param("providerId") String providerId,
                                     @Param("pubSet") long pubSet,
                                     @Param("stability") String stability,
                                     @Param("version") String version);

    LoadAssetsDai.Assets selectAssetByProviderGroupNameStabilityVersion(@Param("providerId") String providerId,
                                                                        @Param("group") String group,
                                                                        @Param("name") String name,
                                                                        @Param("stability") String stability,
                                                                        @Param("version") String version);

    LoadAssetsDai.Assets selectAssetsByProviderModelSubIdStabilityVersion(@Param("providerId") String providerId,
                                                                          @Param("modelSubId") long subId,
                                                                          @Param("stability") String stability,
                                                                          @Param("version") String version);

    void revokeAsset(@Param("providerId") String providerId,
                     @Param("pubSet") long pubSet,
                     @Param("endTime") Date endTime);
}
