package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetPublicationMapper;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
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
    public Date insert(String pubSet, List<Record> records) {
        Date now = new Date();
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
}
