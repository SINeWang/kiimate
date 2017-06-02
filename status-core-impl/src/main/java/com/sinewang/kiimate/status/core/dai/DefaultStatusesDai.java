package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.StatusesMapper;
import one.kii.kiimate.status.core.dai.StatusesDai;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.io.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 27/05/2017.
 */
@Component
public class DefaultStatusesDai implements StatusesDai {

    @Autowired
    private StatusesMapper statusesMapper;


    @Override
    public List<Status> query(ClueGroup clue) {
        return statusesMapper.queryStatuses(
                clue.getOwnerId(),
                clue.getGroup());
    }

    @Override
    public Status load(ChannelPubSet channel) throws NotFound {
        Status status = statusesMapper.selectStatus(
                channel.getOwnerId(),
                channel.getPubSet(),
                channel.getStability(),
                channel.getVersion());
        if (status == null) {
            throw new NotFound(KeyFactorTools.find(ChannelPubSet.class));
        }
        return status;
    }

    @Override
    public Status load(ChannelModelSubId channel) throws NotFound {
        Status status = statusesMapper.selectStatusByProviderModelSubIdStabilityVersion(
                channel.getOwnerId(),
                channel.getSubId(),
                channel.getStability(),
                channel.getVersion());
        if (status == null) {
            throw new NotFound(KeyFactorTools.find(ChannelModelSubId.class));
        }
        return status;
    }

    @Override
    public Status load(ChannelGroupName channel) throws NotFound {
        Status status = statusesMapper.selectStatusByProviderGroupNameStabilityVersion(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getStability(),
                channel.getVersion());
        if (status == null) {
            throw new NotFound(KeyFactorTools.find(ChannelGroupName.class));
        }
        return status;
    }
}
