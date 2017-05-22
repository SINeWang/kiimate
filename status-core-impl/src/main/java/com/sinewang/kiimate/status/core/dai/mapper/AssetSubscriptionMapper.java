package com.sinewang.kiimate.status.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by WangYanJiong on 22/05/2017.
 */

@Mapper
public interface AssetSubscriptionMapper {


    void insert(@Param("id") String id,
                @Param("subscriberId") String subscriberId,
                @Param("subSet") String subSet,
                @Param("insSubId") String insSubId,
                @Param("operatorId") String operatorId,
                @Param("beginTime") Date beginTime);

    int countById(@Param("id") String id);


}
