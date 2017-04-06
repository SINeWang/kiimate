package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.yanjiong.metamate.core.dai.PublicationDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Mapper
public interface PublicationMapper {

    void insertPublication(
            @Param("id") String id,
            @Param("providerId") String providerId,
            @Param("extId") String extId,
            @Param("intId") String intId,
            @Param("version") String version,
            @Param("publication") String publication,
            @Param("operatorId") String operatorId,
            @Param("createdAt") Date createdAt);

    List<PublicationDai.Publication> selectPublicationByProviderIdExtIdPubVersion(
            @Param("providerId") String providerId,
            @Param("extId") String extId,
            @Param("publication") String publication,
            @Param("version") String version);

    void deletePublicationByProviderIdExtIdPubVersion(
            @Param("providerId") String providerId,
            @Param("extId") String extId,
            @Param("publication") String publication,
            @Param("version") String version);
}
