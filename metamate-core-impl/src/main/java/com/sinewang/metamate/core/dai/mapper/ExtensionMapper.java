package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */
@Mapper
public interface ExtensionMapper {

    void insertExtension(@Param("id") String id,
                         @Param("group") String group,
                         @Param("name") String name,
                         @Param("version") String version,
                         @Param("visibility") String visibility,
                         @Param("dataStructure") String dataStructure);

    Extension selectExtensionById(@Param("id") String id);


    void deleteExtensionById(@Param("id") String id);


}
