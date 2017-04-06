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
                        @Param("pubId") String pubId,
                        @Param("value") String value,
                        @Param("valueSetHash") String valueSetHash,
                        @Param("valueRefId") String valueRefId,
                        @Param("operatorId") String operatorId,
                        @Param("beginTime") Date beginTime);

    List<InstanceDai.Instance> selectLatestInstancesByOwnerIdExtId(@Param("extId") String extId,
                                                                   @Param("ownerId") String ownerId);

    List<InstanceDai.Instance> selectLatestInstanceByIntIdOwnerId(@Param("intId") String intId,
                                                                  @Param("ownerId") String ownerId);

    void updateInstanceEndTimeByOwnerIdIntId(@Param("ownerId") String ownerId,
                                             @Param("intId") String intId,
                                             @Param("endTime") Date endTime);

}
