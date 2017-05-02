package com.sinewang.metamate.core.dai.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import wang.yanjiong.metamate.core.dai.ModelPublicationDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Mapper
public interface ModelPublicationMapper {

    void insertPublication(
            @Param("id") String id,
            @Param("pubSetHash") String pubSetHash,
            @Param("providerId") String providerId,
            @Param("extId") String extId,
            @Param("intId") String intId,
            @Param("version") String version,
            @Param("publication") String publication,
            @Param("operatorId") String operatorId,
            @Param("createdAt") Date createdAt);

    List<ModelPublicationDai.Publication> selectPublicationByPubSetHash(
            @Param("pubSetHash") String pubSetHash);

    int countPublicationByPubSetHash(
            @Param("pubSetHash") String pubSetHash);

    void deletePublicationByProviderIdExtIdPubVersion(
            @Param("providerId") String providerId,
            @Param("extId") String extId,
            @Param("publication") String publication,
            @Param("version") String version);

    void deletePublicationByProviderId(@Param("providerId") String providerId);

    List<ModelPublicationDai.Provider> selectProvidersByProviderQuery(@Param("query") String query);

    List<ModelPublicationDai.Publication> selectPublicationByGroupQuery(@Param("query") String query);
}
