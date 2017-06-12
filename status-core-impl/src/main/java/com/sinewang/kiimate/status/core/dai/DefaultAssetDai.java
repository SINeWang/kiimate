package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetsMapper;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.summer.beans.utils.ConflictFinder;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.ZoomInById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
@Component
public class DefaultAssetDai implements AssetDai {

    @Autowired
    private AssetsMapper assetsMapper;


    @Override
    public void remember(Subscription subscription) throws BadRequest {
        NotBadRequest.from(subscription);
        assetsMapper.insertSubscription(
                subscription.getId(),
                subscription.getSubscriberId(),
                subscription.getSubSet(),
                subscription.getGroup(),
                subscription.getName(),
                subscription.getTree(),
                subscription.getOperatorId(),
                subscription.getBeginTime()
        );
    }


    @Override
    public void remember(Publication publication, List<Entry> entries) throws Conflict {
        Map<String, Object> map = ConflictFinder.find(publication);
        Integer count = assetsMapper.countByConflictKey(map);
        if (count > 0) {
            throw new Conflict(map.keySet());
        }
        for (Entry entry : entries) {
            assetsMapper.insertPublication(
                    entry.getId(),
                    publication.getPubSet(),
                    publication.getProviderId(),
                    publication.getModelSubId(),
                    entry.getInsId(),
                    publication.getVersion(),
                    publication.getStability(),
                    publication.getVisibility(),
                    publication.getOperatorId(),
                    publication.getBeginTime()
            );
        }
    }


    @Override
    public List<Providers> queryProviders(ClueId clue) throws BadRequest {
        NotBadRequest.from(clue);
        return assetsMapper.queryProviders(clue.getId());
    }

    @Override
    public Assets load(ZoomInById channel) throws Panic {
        Assets asset = assetsMapper.selectAsset(
                channel.getSubscriberId(),
                channel.getId());
        return NotBadResponse.of(asset);
    }

}
