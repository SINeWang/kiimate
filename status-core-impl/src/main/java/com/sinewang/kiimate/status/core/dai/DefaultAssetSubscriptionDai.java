package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetSubscriptionMapper;
import one.kii.kiimate.status.core.dai.AssetSubscriptionDai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by WangYanJiong on 22/05/2017.
 */

@Component
public class DefaultAssetSubscriptionDai implements AssetSubscriptionDai {

    @Autowired
    private AssetSubscriptionMapper assetSubscriptionMapper;

    @Override
    public void save(Record record) {
        int count = assetSubscriptionMapper.countById(record.getId());
        if (count == 0) {
            assetSubscriptionMapper.insert(
                    record.getId(),
                    record.getSubscriberId(),
                    record.getSubSet(),
                    record.getOperatorId(),
                    record.getBeginTime()
            );
        }
    }
}
