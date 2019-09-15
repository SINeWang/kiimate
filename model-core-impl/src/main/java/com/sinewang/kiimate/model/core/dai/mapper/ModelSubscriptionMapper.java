package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.zoom.InsideView;
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
            @Param("id") String id,
            @Param("subSet") String subSet,
            @Param("subscriberId") String subscriberId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    void deleteById(
            @Param("id") String id);

    int countModelSub(
            @Param("subSet") String subSet);

    List<ModelSubscriptionDai.Instance> queryModelSubByName(
            @Param("ownerId") String ownerId,
            @Param("group") String group);

    InsideView selectModelSubById(
            @Param("subscriberId") String subscriberId,
            @Param("id") String subId);

    InsideView selectModelSubByName(
            @Param("subscriberId") String subscribreId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree);

    List<ModelSubscriptionDai.Subscribers> querySubscriberId(
            @Param("subscriberId") String subscriberId);

    ModelSubscriptionDai.Instance loadInstance(
            @Param("id") String id);

}
