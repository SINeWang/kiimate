package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.summer.zoom.OutsideView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Mapper
public interface StatusMapper {

    void insertStatus(
            @Param("id") Long id,
            @Param("pubSet") Long pubSet,
            @Param("providerId") String providerId,
            @Param("modelSubId") Long subId,
            @Param("insId") Long insId,
            @Param("version") String version,
            @Param("visibility") String visibility,
            @Param("stability") String stability,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime
    );


    OutsideView selectLast(
            @Param("providerId") String providerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("stability") String stability,
            @Param("version") String version,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime
    );

    void revokeStatus(
            @Param("providerId") String providerId,
            @Param("pubSet") Long pubSet,
            @Param("endTime") Date endTime);

}
