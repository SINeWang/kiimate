package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.ExtensionDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@Mapper
public interface ExtensionMapper {

    List<ExtensionDai.Providers> queryProviders(
            @Param("providerId") String providerId);

    void insertExtension(
            @Param("id") Long id,
            @Param("commit") String commit,
            @Param("ownerId") String ownerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("visibility") String visibility,
            @Param("operatorId") String operatorId,
            @Param("beginTime") Date beginTime);

    ExtensionDai.Record selectLastExtensionBySet(
            @Param("set") Long set,
            @Param("endTime") Date endTime);

    ExtensionDai.Record selectLastExtensionById(
            @Param("id") Long id,
            @Param("endTime") Date endTime);

    ExtensionDai.Record selectExtensionByConflictFactor(Map<String, String> map);

    ExtensionDai.Record selectLastExtensionByName(
            @Param("ownerId") String ownerId,
            @Param("group") String group,
            @Param("name") String name,
            @Param("tree") String tree,
            @Param("endTime") Date endTime);


    List<ExtensionDai.Record> queryExtensionsByOwnerGroup(
            @Param("ownerId") String ownerId,
            @Param("group") String group);

    void revoke(@Param("id") Long id,
                @Param("endTime") Date endTime);

}
