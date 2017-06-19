package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.zoom.InsideView;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutBySet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Mapper
public interface ModelSubscriptionMapper {

    int countByConflictKeys(Map<String, String> map);

    void insertSubscription(
            @Param("id") Long id,
            @Param("subSet") Long subSet,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void deleteById(
            @Param("id") Long id);

    int countModelSubscriptions(
            @Param("subSet") Long subSet);

    List<ModelSubscriptionDai.Instance> querySubscriptionsByOwnerGroup(
            @Param("ownerId") String ownerId,
            @Param("group") String group);

    InsideView selectModelSubById(
            @Param("subscriberId") String subscribreId,
            @Param("id") Long subId);

    InsideView selectModelSubByName(
            @Param("subscriberId") String subscribreId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);

    List<ModelSubscriptionDai.Subscribers> querySubscriberId(
            @Param("subscriberId") String subscriberId);

    OutsideView selectModelPubBySet(ZoomOutBySet zoom);

    ModelSubscriptionDai.Instance loadInstance(
            @Param("id") Long id);

}
