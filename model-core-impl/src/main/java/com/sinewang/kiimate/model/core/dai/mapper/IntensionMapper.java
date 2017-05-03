package com.sinewang.kiimate.model.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import one.kii.kiimate.model.core.dai.IntensionDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Mapper
public interface IntensionMapper {


    void insertIntension(@Param("id") String id,
                         @Param("extId") String extId,
                         @Param("field") String field,
                         @Param("single") boolean single,
                         @Param("structure") String structure,
                         @Param("refExtId") String refExtId,
                         @Param("visibility") String visibility,
                         @Param("beginTime") Date beginTime);


    List<IntensionDai.Intension> selectLatestIntensionsByExtId(@Param("extId") String extId);

    IntensionDai.Intension selectLatestIntensionByExtIdField(@Param("extId") String extId, @Param("field") String field);

    void updateLatestIntensionEndTimeById(@Param("id") String id, @Param("endTime") Date endTime);

    void deleteIntensionsByExtId(@Param("extId") String extId);

}
