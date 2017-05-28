package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Mapper
public interface ModelSubscriptionMapper {

    int countLatestSubscription(
            @Param("subSet") String subSet,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);

    void insertSubscription(
            @Param("id") String id,
            @Param("subSet") String subSet,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void deleteById(@Param("id") String id);

    int countModelSubscriptions(@Param("subSet") String subSet);

    List<ModelSubscriptionDai.ModelSubscription> querySubscriptionsByOwnerGroup(
            @Param("ownerId") String ownerId,
            @Param("group") String group);

    ModelSubscriptionDai.ExtensionId selectLatestRootExtIdByOwnerSubscription(
            @Param("ownerId") String ownerId,
            @Param("subId") String subId);

    ModelSubscriptionDai.ModelSubscription selectSubscriptionByOwnerGroupNameTree(
            @Param("ownerId") String ownerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);


    List<ModelSubscriptionDai.Subscribers> querySubscriberId(@Param("subscriberId") String subscriberId);


    ModelSubscriptionDai.ModelSubscription selectByOwnerSubId(
            @Param("ownerId") String ownerId,
            @Param("subId") String subId);
}
