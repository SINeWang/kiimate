package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetsMapper;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public void remember(Publication publication, List<Entry> entries) {
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
    public List<Providers> queryProviders(ClueId clue) {
        return assetsMapper.queryProviders(
                clue.getId());
    }


    @Override
    public List<Assets> query(ClueGroup clue) throws BadRequest {
        NotBadRequest.from(clue);
        return assetsMapper.queryAssets(
                clue.getOwnerId(),
                clue.getGroup());
    }

    @Override
    public Assets load(ChannelOwnerId channel) throws Panic {
        Assets asset = assetsMapper.selectAsset(
                channel.getOwnerId(),
                channel.getId());
        return NotBadResponse.of(asset);
    }

    @Override
    public Publication load(ChannelSubscriptionId channel) throws Panic {
        Publication publication = assetsMapper.selectPublication(
                channel.getId());
        return NotBadResponse.of(publication);
    }

    public Assets load(ChannelGroupName channel) throws Panic {
        Assets asset = assetsMapper.selectAssetByProviderGroupNameStabilityVersion(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getStability(),
                channel.getVersion());
        return NotBadResponse.of(asset);
    }
}
