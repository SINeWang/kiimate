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


    void insertIntension(
            @Param("id") Long id,
            @Param("commit") String commit,
            @Param("extId") Long extId,
            @Param("field") String field,
            @Param("single") Boolean single,
            @Param("structure") String structure,
            @Param("refPubSet") Long refPubSet,
            @Param("visibility") String visibility,
            @Param("required") Boolean required,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);


    List<IntensionDai.Record> selectLatestIntensionsByExtId(
            @Param("extId") Long extId);

    List<IntensionDai.Record> selectLastIntensionsByExtId(
            @Param("extId") Long extId,
            @Param("beginTime") Date beginTime);


    IntensionDai.Record selectLastIntensionByExtIdField(
            @Param("extId") Long extId,
            @Param("field") String field);

    IntensionDai.Record selectLatestIntensionByExtIdField(
            @Param("extId") Long extId,
            @Param("field") String field);

    IntensionDai.Record selectIntensionByCommit(
            @Param("commit") String commit);

    List<String> selectLastFieldsByExtIdPubSet(
            @Param("extId") Long extId,
            @Param("pubSet") Long pubSet,
            @Param("beginTime") Date beginTime);

    void updateLatestIntensionEndTimeById(
            @Param("id") Long id,
            @Param("endTime") Date endTime);

    void deleteIntensionsByExtId(
            @Param("extId") Long extId);

}
