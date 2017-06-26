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

    OutsideView selectLast(
            @Param("providerId") String providerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("stability") String stability,
            @Param("version") String version,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime
    );

}
