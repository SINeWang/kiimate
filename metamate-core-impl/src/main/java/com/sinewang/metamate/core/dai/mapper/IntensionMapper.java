package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.yanjiong.metamate.core.dai.IntensionDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Mapper
public interface IntensionMapper {


    void insertIntension(@Param("id") String id,
                         @Param("extId") String extId,
                         @Param("name") String rename,
                         @Param("single") boolean single,
                         @Param("structure") String structure,
                         @Param("visibility") String visibility,
                         @Param("beginTime") Date beginTime);

    List<IntensionDai.Intension> selectIntensionsByExtId(@Param("extId") String extId);
}
