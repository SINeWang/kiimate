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
                        @Param("extId") String extId,
                        @Param("intId") String intId,
                        @Param("ownerId") String ownerId,
                        @Param("field") String field,
                        @Param("value") String value,
                        @Param("valueRefId") String valueRefId,
                        @Param("operatorId") String operatorId,
                        @Param("beginTime") Date beginTime);

    List<InstanceDai.Instance> selectInstancesByOwnerIdExtId(@Param("ownerId") String ownerId,
                                                             @Param("extId") String extId);


    InstanceDai.Instance selectLatestInstanceByIntIdOwnerId(@Param("intId") String intId,
                                                            @Param("ownerId") String ownerId);

    void updateInstanceEndTimeById(@Param("id") String id,
                                   @Param("endTime") Date endTime);

}
