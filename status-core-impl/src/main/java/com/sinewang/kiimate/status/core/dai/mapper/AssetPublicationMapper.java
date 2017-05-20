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
            @Param("ownerId") String ownerId,
            @Param("modelSubId") String modelSubId,
            @Param("insId") String insId,
            @Param("version") String version,
            @Param("visibility") String visibility,
            @Param("stability") String stability,
            @Param("beginTime") Date beginTime
    );

    List<AssetPublicationDai.Owners> queryOwners(@Param("ownerId") String ownerId);
}
