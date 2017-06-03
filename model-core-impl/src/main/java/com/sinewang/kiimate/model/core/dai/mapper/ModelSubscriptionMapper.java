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
            @Param("subSet") Long subSet,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);

    void insertSubscription(
            @Param("id") Long id,
            @Param("subSet") Long subSet,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void deleteById(@Param("id") Long id);

    int countModelSubscriptions(@Param("subSet") Long subSet);

    List<ModelSubscriptionDai.Status> querySubscriptionsByOwnerGroup(
            @Param("ownerId") String ownerId,
            @Param("group") String group);

    ModelSubscriptionDai.ModelPubSet selectModelPubSetByOwnerSubscription(
            @Param("ownerId") String ownerId,
            @Param("subId") Long subId);

    ModelSubscriptionDai.Status selectSubscriptionByOwnerGroupNameTree(
            @Param("ownerId") String ownerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);


    List<ModelSubscriptionDai.Subscribers> querySubscriberId(@Param("subscriberId") String subscriberId);


    ModelSubscriptionDai.Status selectByOwnerSubId(
            @Param("ownerId") String ownerId,
            @Param("subId") Long subId);
}
