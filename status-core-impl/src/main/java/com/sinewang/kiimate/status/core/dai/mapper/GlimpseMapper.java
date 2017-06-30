package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.kiimate.status.core.dai.GlimpsesDai;
import one.kii.summer.zoom.OutsideView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Mapper
public interface GlimpseMapper {

    List<GlimpsesDai.Providers> queryProviders(
            @Param("providerId") String providerId
    );

    List<GlimpsesDai.Subscribers> querySubscribers(
            @Param("subscriberId") String subscriberId
    );

    List<GlimpsesDai.Publication> queryStatusPub(
            @Param("providerId") String providerId,
            @Param("group") String group
    );

    List<OutsideView> queryGlimpses(
            @Param("subscriberId") String subscriberId,
            @Param("group") String group
    );

    GlimpsesDai.Publication loadStatusPub(
            @Param("providerId") String providerId,
            @Param("set") String set);

    GlimpsesDai.Publication loadGlimpse(
            @Param("subscriberId") String subscriberId,
            @Param("id") String id);

    void insertGlimpse(
            @Param("id") String id,
            @Param("subscriberId") String subscriberId,
            @Param("set") String set,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void insertPublication(
            @Param("id") String id,
            @Param("set") String pubSet,
            @Param("providerId") String providerId,
            @Param("modelSubId") String modelSubId,
            @Param("insId") String insId,
            @Param("version") String version,
            @Param("stability") String stability,
            @Param("visibility") String visibility,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    int countByConflictKey(Map<String, String> map);

}
