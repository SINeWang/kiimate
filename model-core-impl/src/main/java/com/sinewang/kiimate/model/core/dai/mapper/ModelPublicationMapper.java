package com.sinewang.kiimate.model.core.dai.mapper;

import one.kii.kiimate.model.core.dai.ModelPublicationDai;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutBySet;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 05/04/2017.
 */
@Mapper
public interface ModelPublicationMapper {

    void insertPublication(
            @Param("id") Long id,
            @Param("pubSet") Long pubSet,
            @Param("providerId") String providerId,
            @Param("extId") Long extId,
            @Param("intId") Long intId,
            @Param("version") String version,
            @Param("stability") String stability,
            @Param("operatorId") String operatorId,
            @Param("createdAt") Date createdAt);

    int countByConflictKey(Map<String, String> map);

    void deletePublicationByProviderIdExtIdPubVersion(
            @Param("providerId") String providerId,
            @Param("extId") Long extId,
            @Param("stability") String stability,
            @Param("version") String version);

    void deletePublicationByProviderId(@Param("providerId") String providerId);

    List<ModelPublicationDai.Provider> queryProviders(@Param("query") String query);

    List<ModelPublicationDai.PublishedExtension> selectPublishedExtensionByGroupQuery(@Param("query") String query);

    List<ModelPublicationDai.PublishedSnapshot> selectPublishedSnapshotsByExtId(@Param("extId") Long extId);

    List<ModelPublicationDai.Record> selectPublicationsBySet(@Param("set") Long set);

    OutsideView selectModelPubBySet(ZoomOutBySet zoom);
}
