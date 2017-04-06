package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.yanjiong.metamate.core.dai.ModelSubscriptionDai;

import java.util.Date;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Mapper
public interface ModelSubscriptionMapper {

    void insertSubscription(
            @Param("id") String id,
            @Param("providerId") String providerId,
            @Param("extId") String extId,
            @Param("publication") String publication,
            @Param("version") String version,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void deleteById(@Param("id") String id);

    ModelSubscriptionDai.ModelSubscription selectLatestSubscriptionBySubscriberIdGroupNameTree(
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);


}
