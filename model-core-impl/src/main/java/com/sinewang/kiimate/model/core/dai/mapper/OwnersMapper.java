package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.OwnersDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by WangYanJiong on 08/05/2017.
 */

@Mapper
public interface OwnersMapper {

    List<OwnersDai.Owners> queryOwners(@Param("ownerId") String ownerId);
}
