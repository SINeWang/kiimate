package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

import java.util.Date;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@Mapper
public interface ExtensionMapper {

    void insertExtension(@Param("id") String id,
                         @Param("group") String group,
                         @Param("name") String name,
                         @Param("tree") String tree,
                         @Param("visibility") String visibility,
                         @Param("structure") String structure,
                         @Param("beginTime") Date beginTime);

    ExtensionDai.Extension selectExtensionById(@Param("id") String id);

    void deleteExtensionById(@Param("id") String id);

    void updateEndTimeExtensionById(@Param("id") String id,
                                    @Param("endTime") Date endTime);

}
