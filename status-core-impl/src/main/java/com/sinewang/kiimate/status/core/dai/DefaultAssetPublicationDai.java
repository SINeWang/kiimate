package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.StatusesMapper;
import one.kii.kiimate.status.core.dai.AssetPublicationDai;
import one.kii.kiimate.status.core.dai.StatusesDai;
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
    private StatusesMapper statusesMapper;


    @Override
    public List<Providers> queryProviders(ClueId clue) {
        return statusesMapper.queryProviders(
                clue.getId());
    }

    @Override
    public Date save(Record record) {
        Date now = new Date();
        StatusesDai.Status previous = record.getPrevious();
        if (previous != null) {
            statusesMapper.revokeAsset(
                    previous.getProviderId(),
                    previous.getPubSet(),
                    now
            );
        }
        for (Entry entry : record.getEntries()) {
            statusesMapper.insertStatus(
                    entry.getId(),
                    record.getPubSet(),
                    entry.getProviderId(),
                    entry.getSubId(),
                    entry.getInsId(),
                    entry.getVersion(),
                    entry.getVisibility(),
                    entry.getStability(),
                    record.getOperatorId(),
                    now
            );
        }
        return now;
    }
}
