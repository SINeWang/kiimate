package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelSubscriptionMapper;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.validator.Must;
import one.kii.summer.io.validator.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultModelSubscriptionDai implements ModelSubscriptionDai {


    @Autowired
    private ModelSubscriptionMapper modelSubscriptionMapper;


    @Override
    public void remember(Status status) throws DuplicatedSubscription {
        int count = modelSubscriptionMapper.countLatestSubscription(
                status.getSubSet(),
                status.getSubscriberId(),
                status.getGroup(),
                status.getName(),
                status.getTree()
        );
        if (count > 0) {
            throw new DuplicatedSubscription(
                    status.getSubSet(),
                    status.getSubscriberId(),
                    status.getGroup(),
                    status.getName(),
                    status.getTree()
            );
        }
        Date now = new Date();
        modelSubscriptionMapper.insertSubscription(
                status.getId(),
                status.getSubSet(),
                status.getSubscriberId(),
                status.getGroup(),
                status.getName(),
                status.getTree(),
                status.getOperatorId(),
                now
        );
    }

    @Override
    public ModelPubSet getModelPubSetByOwnerSubscription(ChannelSubId channel) throws NotFound {
        ModelPubSet record = modelSubscriptionMapper.selectModelPubSetByOwnerSubscription(
                channel.getOwnerId(),
                channel.getId());
        return NotNull.of(ModelPubSet.class, record);
    }

    @Override
    public List<Status> querySubscriptions(ClueGroup clue) {
        return modelSubscriptionMapper.querySubscriptionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
    }

    @Override
    public Status selectSubscription(ChannelGroupNameTree channel) throws NotFound, BadRequest {
        Must.have(channel);
        Status record = modelSubscriptionMapper.selectSubscriptionByOwnerGroupNameTree(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getTree()
        );
        return NotNull.of(Status.class, record);

    }

    @Override
    public Status selectSubscription(ChannelSubId channel) throws NotFound {
        Status record = modelSubscriptionMapper.selectByOwnerSubId(
                channel.getOwnerId(),
                channel.getId()
        );
        return NotNull.of(Status.class, record);
    }

    @Override
    public Integer countModelSubscriptions(Long pubSet) {
        return modelSubscriptionMapper.countModelSubscriptions(pubSet);
    }

    @Override
    public List<Subscribers> querySubscribers(ClueSubscriberId clue) {
        return modelSubscriptionMapper.querySubscriberId(
                clue.getId());
    }


}
