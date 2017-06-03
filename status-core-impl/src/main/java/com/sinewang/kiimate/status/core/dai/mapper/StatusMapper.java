package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.kiimate.status.core.dai.StatusDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    List<StatusDai.Statuses> queryStatuses(
            @Param("ownerId") String ownerId,
            @Param("group") String group
    );

    void revokeStatus(
            @Param("providerId") String providerId,
            @Param("pubSet") Long pubSet,
            @Param("endTime") Date endTime);

}
