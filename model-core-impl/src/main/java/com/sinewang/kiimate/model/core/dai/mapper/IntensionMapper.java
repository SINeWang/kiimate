package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.IntensionDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Mapper
public interface IntensionMapper {


    void insertIntension(@Param("id") long id,
                         @Param("extId") long extId,
                         @Param("field") String field,
                         @Param("single") boolean single,
                         @Param("structure") String structure,
                         @Param("refPubSet") long refPubSet,
                         @Param("visibility") String visibility,
                         @Param("required") boolean required,
                         @Param("beginTime") Date beginTime);


    List<IntensionDai.Intension> selectLatestIntensionsByExtId(@Param("extId") long extId);

    IntensionDai.Intension selectLastIntensionByExtIdField(@Param("extId") long extId, @Param("field") String field);

    IntensionDai.Intension selectLatestIntensionByExtIdField(@Param("extId") long extId, @Param("field") String field);

    List<String> selectLastFieldsByExtIdPubSet(
            @Param("extId") long extId,
            @Param("pubSet") long pubSet,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime);

    void updateLatestIntensionEndTimeById(@Param("id") long id, @Param("endTime") Date endTime);

    void deleteIntensionsByExtId(@Param("extId") long extId);

}
