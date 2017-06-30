package com.sinewang.kiimate.status.core.dai.mapper;

import com.sinewang.kiimate.status.core.dai.DefaultInstanceDai;
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
            @Param("id") String id,
            @Param("commit") String commit,
            @Param("ownerId") String ownerId,
            @Param("subId") String subId,
            @Param("extId") String extId,
            @Param("intId") String intId,
            @Param("field") String field,
            @Param("value") String value,
            @Param("valueSet") String valueSet,
            @Param("glimpseId") String glimpseId,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    List<DefaultInstanceDai.Record> selectLastInstancesById(
            @Param("subscriberId") String subscriberId,
            @Param("subId") String subId,
            @Param("beginTime") Date beginTime,
            @Param("endTime") Date endTime);

    List<DefaultInstanceDai.Record> selectLatestRecordById(
            @Param("subId") String subId,
            @Param("intId") String intId);

    void revokeValue(
            @Param("subId") String subId,
            @Param("intId") String intId,
            @Param("endTime") Date endTime);

    void deleteInstanceByOwnerId(
            @Param("ownerId") String ownerId,
            @Param("subId") String subId);


}
