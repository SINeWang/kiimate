package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

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

    void deleteExtensionById(@Param("id") String id);

    void updateEndTimeExtensionById(@Param("id") String id,
                                    @Param("endTime") Date endTime);

}
