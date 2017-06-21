package com.sinewang.kiimate.status.core.dai.mapper;

import one.kii.kiimate.status.core.dai.InstanceDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Mapper
public interface InstanceMapper {


    void insertInstance(
            @Param("id") Long id,
            @Param("commit") String commit,
            @Param("ownerId") String ownerId,
            @Param("subId") Long subId,
            @Param("extId") Long extId,
            @Param("intId") Long intId,
            @Param("field") String field,
            @Param("value") String value,
            @Param("valueSet") Long valueSet,
            @Param("valueRefId") Long valueRefId,
            @Param("valueRefPolicy") String valueRefPolicy,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    List<InstanceDai.Record> selectLastInstancesById(
            @Param("subscriberId") String subscriberId,
            @Param("subId") Long subId,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime);

    List<InstanceDai.Record> selectLatestValueById(
            @Param("subId") Long subId,
            @Param("intId") Long intId);

    void revokeValue(
            @Param("subId") Long subId,
            @Param("intId") Long intId,
            @Param("endTime") Date endTime);

    void deleteInstanceByOwnerId(
            @Param("ownerId") String ownerId,
            @Param("subId") Long subId);


}
