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
            @Param("subSet") long subSet,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);

    void insertSubscription(
            @Param("id") long id,
            @Param("subSet") long subSet,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void deleteById(@Param("id") long id);

    int countModelSubscriptions(@Param("subSet") long subSet);

    List<ModelSubscriptionDai.ModelSubscription> querySubscriptionsByOwnerGroup(
            @Param("ownerId") String ownerId,
            @Param("group") String group);

    ModelSubscriptionDai.ModelPubSet selectModelPubSetByOwnerSubscription(
            @Param("ownerId") String ownerId,
            @Param("subId") long subId);

    ModelSubscriptionDai.ModelSubscription selectSubscriptionByOwnerGroupNameTree(
            @Param("ownerId") String ownerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);


    List<ModelSubscriptionDai.Subscribers> querySubscriberId(@Param("subscriberId") String subscriberId);


    ModelSubscriptionDai.ModelSubscription selectByOwnerSubId(
            @Param("ownerId") String ownerId,
            @Param("subId") long subId);
}
