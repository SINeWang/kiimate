package com.sinewang.kiimate.model.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import one.kii.kiimate.model.core.dai.ExtensionDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@Mapper
public interface ExtensionMapper {

    void insertExtension(@Param("id") String id,
                         @Param("ownerId") String ownerId,
                         @Param("group") String group,
                         @Param("name") String name,
                         @Param("tree") String tree,
                         @Param("visibility") String visibility,
                         @Param("beginTime") Date beginTime);

    ExtensionDai.Extension selectExtensionById(@Param("id") String id);

    List<ExtensionDai.Extension> selectExtensionsByOwnerGroup(@Param("ownerId") String ownerId, @Param("group") String group);

    List<ExtensionDai.Extension> queryExtensionsByOwnerGroup(@Param("ownerId") String ownerId, @Param("group") String group);

    void deleteExtensionById(@Param("id") String id);

    void updateEndTimeExtensionByOwnerIdGroup(
            @Param("ownerId") String ownerId,
            @Param("group") String group,
            @Param("endTime") Date endTime);

}
