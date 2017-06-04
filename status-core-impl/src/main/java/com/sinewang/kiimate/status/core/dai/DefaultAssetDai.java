package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetsMapper;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
@Component
public class DefaultAssetDai implements AssetDai {

    @Autowired
    private AssetsMapper assetsMapper;


    @Override
    public void remember(Subscription subscription) {
        int count = assetsMapper.countById(subscription.getId());
        if (count == 0) {
            assetsMapper.insert(
                    subscription.getId(),
                    subscription.getSubscriberId(),
                    subscription.getSubSet(),
                    subscription.getOperatorId(),
                    subscription.getBeginTime()
            );
        }
    }

    @Override
    public Date remember(Publication publication) {
        Date now = new Date();

        return now;
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
    public Assets load(ChannelPubSet channel) throws Panic {
        Assets asset = assetsMapper.selectAsset(
                channel.getOwnerId(),
                channel.getPubSet(),
                channel.getStability(),
                channel.getVersion());
        return NotBadResponse.of(Assets.class, MayHave.class, asset);
    }

    @Override
    public Assets load(ChannelModelSubId channel) throws Panic {
        Assets asset = assetsMapper.selectAssetByProviderModelSubIdStabilityVersion(
                channel.getOwnerId(),
                channel.getSubId(),
                channel.getStability(),
                channel.getVersion());
        return NotBadResponse.of(Assets.class, MayHave.class, asset);
    }

    @Override
    public Assets load(ChannelGroupName channel) throws Panic {
        Assets asset = assetsMapper.selectAssetByProviderGroupNameStabilityVersion(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getStability(),
                channel.getVersion());
        return NotBadResponse.of(Assets.class, MayHave.class, asset);
    }
}
