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


    void insertInstance(@Param("id") Long id,
                        @Param("commit") String commit,
                        @Param("ownerId") String ownerId,
                        @Param("subId") Long subId,
                        @Param("extId") Long extId,
                        @Param("intId") Long intId,
                        @Param("field") String field,
                        @Param("value") String value,
                        @Param("valueSetHash") Long valueSet,
                        @Param("valueRefPath") String valueRefPath,
                        @Param("valueRefPolicy") String valueRefPolicy,
                        @Param("operatorId") String operatorId,
                        @Param("beginTime") Date beginTime);

    List<InstanceDai.Instance> selectLatestInstancesByAssetId(@Param("subId") Long subId);

    List<InstanceDai.Instance> selectInstancesByPubSet(@Param("pubSet") Long putSet);

    List<InstanceDai.Instance> selectLatestInstanceBySubIdIntId(@Param("subId") Long subId,
                                                                @Param("intId") Long intId);

    void updateInstanceEndTimeBySubIdIntId(@Param("subId") Long subId,
                                           @Param("intId") Long intId,
                                           @Param("endTime") Date endTime);

    void deleteInstanceByOwnerId(@Param("ownerId") String ownerId,
                                 @Param("subId") Long subId);


}
