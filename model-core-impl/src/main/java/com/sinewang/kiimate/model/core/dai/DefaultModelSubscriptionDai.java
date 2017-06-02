package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelSubscriptionMapper;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.utils.MustHaveTools;
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
    public void remember(ModelSubscription modelSubscription) throws DuplicatedSubscription {
        int count = modelSubscriptionMapper.countLatestSubscription(
                modelSubscription.getSubSet(),
                modelSubscription.getSubscriberId(),
                modelSubscription.getGroup(),
                modelSubscription.getName(),
                modelSubscription.getTree()
        );
        if (count > 0) {
            throw new DuplicatedSubscription(
                    modelSubscription.getSubSet(),
                    modelSubscription.getSubscriberId(),
                    modelSubscription.getGroup(),
                    modelSubscription.getName(),
                    modelSubscription.getTree()
            );
        }
        Date now = new Date();
        modelSubscriptionMapper.insertSubscription(
                modelSubscription.getId(),
                modelSubscription.getSubSet(),
                modelSubscription.getSubscriberId(),
                modelSubscription.getGroup(),
                modelSubscription.getName(),
                modelSubscription.getTree(),
                modelSubscription.getOperatorId(),
                now
        );
    }

    @Override
    public ModelPubSet getModelPubSetByOwnerSubscription(ChannelSubId channel) throws NotFound {


        ModelPubSet record = modelSubscriptionMapper.selectModelPubSetByOwnerSubscription(
                channel.getOwnerId(),
                channel.getSubId());
        if (record == null) {
            throw new NotFound(MustHaveTools.find(ChannelSubId.class));
        }
        return record;
    }

    @Override
    public List<ModelSubscription> querySubscriptions(ClueGroup clue) {
        return modelSubscriptionMapper.querySubscriptionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
    }

    @Override
    public ModelSubscription selectSubscription(ChannelGroupNameTree channel) throws NotFound {
        ModelSubscription record = modelSubscriptionMapper.selectSubscriptionByOwnerGroupNameTree(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getTree()
        );
        if (record == null) {
            throw new NotFound(MustHaveTools.find(ChannelGroupNameTree.class));
        }
        return record;
    }

    @Override
    public ModelSubscription selectSubscription(ChannelSubId channel) throws NotFound {
        ModelSubscription record = modelSubscriptionMapper.selectByOwnerSubId(
                channel.getOwnerId(),
                channel.getSubId()
        );
        if (record == null) {
            throw new NotFound(MustHaveTools.find(ChannelSubId.class));
        }
        return record;
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
