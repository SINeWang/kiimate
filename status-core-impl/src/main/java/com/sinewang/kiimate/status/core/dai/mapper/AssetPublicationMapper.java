package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.kiimate.status.core.dai.AssetPublicationDai;
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
            @Param("id") String id,
            @Param("pubSet") String pubSet,
            @Param("providerId") String providerId,
            @Param("modelSubId") String modelSubId,
            @Param("insId") String insId,
            @Param("version") String version,
            @Param("visibility") String visibility,
            @Param("stability") String stability,
            @Param("beginTime") Date beginTime
    );

    List<AssetPublicationDai.Providers> queryProviders(@Param("providerId") String providerId);

    List<AssetPublicationDai.Assets> queryAssets(@Param("providerId") String providerId,
                                                 @Param("group") String group);

    AssetPublicationDai.Assets selectAsset(@Param("providerId") String providerId,
                                           @Param("pubSet") String pubSet,
                                           @Param("version") String version);

    AssetPublicationDai.Assets selectAssetByProviderGroupNameStabilityVersion(@Param("providerId") String providerId,
                                                                              @Param("group") String group,
                                                                              @Param("name") String name,
                                                                              @Param("stability") String stability,
                                                                              @Param("version") String version);
}
