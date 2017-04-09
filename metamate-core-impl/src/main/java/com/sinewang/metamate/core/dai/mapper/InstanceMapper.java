package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.yanjiong.metamate.core.dai.InstanceDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Mapper
public interface InstanceMapper {


    void insertInstance(@Param("id") String id,
                        @Param("ownerId") String ownerId,
                        @Param("subId") String subId,
                        @Param("extId") String extId,
                        @Param("intId") String intId,
                        @Param("field") String field,
                        @Param("value") String value,
                        @Param("valueSetHash") String valueSetHash,
                        @Param("valueRefId") String valueRefId,
                        @Param("operatorId") String operatorId,
                        @Param("beginTime") Date beginTime);

    List<InstanceDai.Instance> selectLatestInstancesBySubId(@Param("subId") String subId);

    List<InstanceDai.Instance> selectLatestInstanceBySubIdIntId(@Param("subId") String subId,
                                                                @Param("intId") String intId);

    void updateInstanceEndTimeBySubIdIntId(@Param("subId") String subId,
                                           @Param("intId") String intId,
                                           @Param("endTime") Date endTime);

    void deleteInstanceByOwnerId(@Param("ownerId") String ownerId,
                                 @Param("subId") String subId);

}
