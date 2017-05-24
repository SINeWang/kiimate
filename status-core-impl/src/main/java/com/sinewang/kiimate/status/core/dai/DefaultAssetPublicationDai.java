package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetPublicationMapper;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Component
public class DefaultAssetPublicationDai implements AssetPublicationDai {


    @Autowired
    private AssetPublicationMapper assetPublicationMapper;


    @Override
    public Date save(String pubSet, List<Record> records, Assets previousAsset) {
        Date now = new Date();
        if (previousAsset != null) {
            assetPublicationMapper.revokeAsset(
                    previousAsset.getProviderId(),
                    previousAsset.getPubSet(),
                    now
            );
        }
        for (Record record : records) {
            assetPublicationMapper.insertAssetPublication(
                    record.getId(),
                    pubSet,
                    record.getProviderId(),
                    record.getModelSubId(),
                    record.getInsId(),
                    record.getVersion(),
                    record.getVisibility(),
                    record.getStability(),
                    now
            );
        }
        return now;
    }

    @Override
    public List<Providers> queryProviders(String providerId) {
        return assetPublicationMapper.queryProviders(providerId);
    }

    @Override
    public List<Assets> queryAssets(String providerId, String group) {
        return assetPublicationMapper.queryAssets(providerId, group);
    }

    @Override
    public Assets selectAssetsPubSet(String ownerId, String pubSet, String stability, String version) {
        return assetPublicationMapper.selectAsset(ownerId, pubSet, stability, version);
    }

    @Override
    public Assets selectAssetsByModelSubId(String providerId, String modelSubId, String stability, String version) throws NotFound {
        Assets assets =
                assetPublicationMapper.selectAssetsByProviderModelSubIdStabilityVersion(providerId, modelSubId, stability, version);
        if (assets == null) {
            throw new NotFound(KeyFactorTools.find(Assets.class));
        }
        return assets;
    }

    @Override
    public Assets selectAssets(String providerId, String group, String name, String stability, String version) throws NotFound {
        Assets assets =
                assetPublicationMapper.selectAssetByProviderGroupNameStabilityVersion(providerId, group, name, stability, version);
        if (assets == null) {
            throw new NotFound(KeyFactorTools.find(Assets.class));
        }
        return assets;
    }
}
