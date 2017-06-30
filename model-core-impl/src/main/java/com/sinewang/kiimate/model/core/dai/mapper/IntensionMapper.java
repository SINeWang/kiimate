package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.IntensionDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Mapper
public interface IntensionMapper {


    void insertIntension(
            @Param("id") String id,
            @Param("commit") String commit,
            @Param("extId") String extId,
            @Param("field") String field,
            @Param("single") Boolean single,
            @Param("structure") String structure,
            @Param("refSet") String refSet,
            @Param("visibility") String visibility,
            @Param("required") Boolean required,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    List<IntensionDai.Record> selectLastIntensionsByExtId(
            @Param("extId") String extId,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime);


    List<IntensionDai.Record> selectIntensionsBySet(
            @Param("set") String set);

    IntensionDai.Record selectLatestIntensionByExtIdField(
            @Param("extId") String extId,
            @Param("field") String field);

    IntensionDai.Record selectIntensionByConflictKey(
            Map<String, String> map);


    void revoke(
            @Param("id") String id,
            @Param("endTime") Date endTime);

    void deleteByExtId(
            @Param("extId") Long extId);

}
