package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Mapper
public interface ModelPublicationMapper {

    void insertPublication(
            @Param("id") String id,
            @Param("pubSet") String pubSet,
            @Param("providerId") String providerId,
            @Param("extId") String extId,
            @Param("intId") String intId,
            @Param("version") String version,
            @Param("stability") String stability,
            @Param("operatorId") String operatorId,
            @Param("createdAt") Date createdAt);

    int countPublicationByPubSet(
            @Param("pubSet") String pubSet);

    void deletePublicationByProviderIdExtIdPubVersion(
            @Param("providerId") String providerId,
            @Param("extId") String extId,
            @Param("stability") String stability,
            @Param("version") String version);

    void deletePublicationByProviderId(@Param("providerId") String providerId);

    List<ModelPublicationDai.Provider> selectProvidersByProviderQuery(@Param("query") String query);

    List<ModelPublicationDai.PublishedExtension> selectPublishedExtensionByGroupQuery(@Param("query") String query);

    List<ModelPublicationDai.PublishedSnapshot> selectPublishedSnapshotsByExtId(@Param("extId") String extId);

    ModelPublicationDai.Publication selectPublicationsByPubSet(@Param("pubSet") String pubSet);
}
