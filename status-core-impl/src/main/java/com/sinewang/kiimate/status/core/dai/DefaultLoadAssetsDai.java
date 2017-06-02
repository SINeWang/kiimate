package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.AssetPublicationMapper;
import one.kii.kiimate.status.core.dai.LoadAssetsDai;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
@Component
public class DefaultLoadAssetsDai implements LoadAssetsDai {

    @Autowired
    private AssetPublicationMapper assetPublicationMapper;


    @Override
    public List<Assets> queryAssets(ClueGroup clue) {
        return assetPublicationMapper.queryAssets(
                clue.getOwnerId(),
                clue.getGroup());
    }

    @Override
    public Assets loadAssets(ChannelPubSet channel) throws NotFound {
        Assets assets = assetPublicationMapper.selectAsset(
                channel.getOwnerId(),
                channel.getPubSet(),
                channel.getStability(),
                channel.getVersion());
        if (assets == null) {
            throw new NotFound(KeyFactorTools.find(ChannelPubSet.class));
        }
        return assets;
    }

    @Override
    public Assets loadAssets(ChannelModelSubId channel) throws NotFound {
        Assets assets = assetPublicationMapper.selectAssetsByProviderModelSubIdStabilityVersion(
                channel.getOwnerId(),
                channel.getSubId(),
                channel.getStability(),
                channel.getVersion());
        if (assets == null) {
            throw new NotFound(KeyFactorTools.find(ChannelModelSubId.class));
        }
        return assets;
    }

    @Override
    public Assets loadAssets(ChannelGroupName channel) throws NotFound {
        Assets assets = assetPublicationMapper.selectAssetByProviderGroupNameStabilityVersion(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getStability(),
                channel.getVersion());
        if (assets == null) {
            throw new NotFound(KeyFactorTools.find(ChannelGroupName.class));
        }
        return assets;
    }
}
