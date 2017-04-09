package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Mapper
public interface ModelSubscriptionMapper {

    void insertSubscription(
            @Param("id") String id,
            @Param("subSetHash") String subSetHash,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void deleteById(@Param("id") String id);

    String selectLatestSubscriptionBySubscriberIdGroupNameTree(
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);

    String selectLatestSubIdSubscriberIdGroupNameTree(
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);


}
