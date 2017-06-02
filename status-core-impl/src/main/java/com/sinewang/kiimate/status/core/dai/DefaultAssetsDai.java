package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetsMapper;
import one.kii.kiimate.status.core.dai.AssetsDai;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
@Component
public class DefaultAssetsDai implements AssetsDai {

    @Autowired
    private AssetsMapper assetsMapper;


    @Override
    public List<Asset> query(ClueGroup clue) {
        return assetsMapper.queryAssets(
                clue.getOwnerId(),
                clue.getGroup());
    }

    @Override
    public Asset load(ChannelPubSet channel) throws NotFound {
        Asset asset = assetsMapper.selectAsset(
                channel.getOwnerId(),
                channel.getPubSet(),
                channel.getStability(),
                channel.getVersion());
        if (asset == null) {
            throw new NotFound(KeyFactorTools.find(ChannelPubSet.class));
        }
        return asset;
    }

    @Override
    public Asset load(ChannelModelSubId channel) throws NotFound {
        Asset asset = assetsMapper.selectAssetByProviderModelSubIdStabilityVersion(
                channel.getOwnerId(),
                channel.getSubId(),
                channel.getStability(),
                channel.getVersion());
        if (asset == null) {
            throw new NotFound(KeyFactorTools.find(ChannelModelSubId.class));
        }
        return asset;
    }

    @Override
    public Asset load(ChannelGroupName channel) throws NotFound {
        Asset asset = assetsMapper.selectAssetByProviderGroupNameStabilityVersion(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getStability(),
                channel.getVersion());
        if (asset == null) {
            throw new NotFound(KeyFactorTools.find(ChannelGroupName.class));
        }
        return asset;
    }
}
