package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetPublicationMapper;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.LoadAssetsDai;
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
    public List<Providers> queryProviders(ClueId clue) {
        return assetPublicationMapper.queryProviders(
                clue.getId());
    }

    @Override
    public Date save(Record record) {
        Date now = new Date();
        LoadAssetsDai.Assets previous = record.getPrevious();
        if (previous != null) {
            assetPublicationMapper.revokeAsset(
                    previous.getProviderId(),
                    previous.getPubSet(),
                    now
            );
        }
        for (Entry entry : record.getEntries()) {
            assetPublicationMapper.insertAssetPublication(
                    entry.getId(),
                    record.getPubSet(),
                    entry.getProviderId(),
                    entry.getModelSubId(),
                    entry.getInsId(),
                    entry.getVersion(),
                    entry.getVisibility(),
                    entry.getStability(),
                    now
            );
        }
        return now;
    }
}
