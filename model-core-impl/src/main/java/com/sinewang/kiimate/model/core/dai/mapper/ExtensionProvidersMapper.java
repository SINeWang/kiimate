package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.ExtensionProvidersDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WangYanJiong on 08/05/2017.
 */

@Mapper
public interface ExtensionProvidersMapper {

    List<ExtensionProvidersDai.Providers> queryOwners(@Param("ownerId") String ownerId);
}
