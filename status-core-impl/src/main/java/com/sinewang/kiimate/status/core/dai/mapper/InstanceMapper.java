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


    void insertInstance(@Param("id") long id,
                        @Param("ownerId") String ownerId,
                        @Param("subId") long subId,
                        @Param("extId") long extId,
                        @Param("intId") long intId,
                        @Param("field") String field,
                        @Param("value") String value,
                        @Param("valueSetHash") long valueSet,
                        @Param("valueRefPath") String valueRefPath,
                        @Param("valueRefPolicy") String valueRefPolicy,
                        @Param("operatorId") String operatorId,
                        @Param("beginTime") Date beginTime);

    List<InstanceDai.Instance> selectLatestInstancesBySubId(@Param("subId") long subId);

    List<InstanceDai.Instance> selectInstancesByPubSet(@Param("pubSet") long putSet);

    List<InstanceDai.Instance> selectLatestInstanceBySubIdIntId(@Param("subId") long subId,
                                                                @Param("intId") long intId);

    void updateInstanceEndTimeBySubIdIntId(@Param("subId") long subId,
                                           @Param("intId") long intId,
                                           @Param("endTime") Date endTime);

    void deleteInstanceByOwnerId(@Param("ownerId") String ownerId,
                                 @Param("subId") long subId);


}
