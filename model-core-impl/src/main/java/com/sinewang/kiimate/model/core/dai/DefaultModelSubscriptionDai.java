package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelSubscriptionMapper;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
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
    public void save(ModelSubscription modelSubscription) throws DuplicatedSubscription {
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
    public ExtensionId getLatestRootExtIdByOwnerSubscription(String owner, String subId) {
        return modelSubscriptionMapper.selectLatestRootExtIdByOwnerSubscription(owner, subId);
    }

    @Override
    public List<ModelSubscription> querySubscriptions(ClueGroup clue) {
        return modelSubscriptionMapper.querySubscriptionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
    }

    @Override
    public ModelSubscription selectSubscription(ChannelGroupNameTree channel) {
        return modelSubscriptionMapper.selectSubscriptionByOwnerGroupNameTree(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getTree()
        );
    }

    @Override
    public ModelSubscription selectSubscription(ChannelSubId channel) {
        return modelSubscriptionMapper.selectByOwnerSubId(
                channel.getOwnerId(),
                channel.getSubId()
        );
    }

    @Override
    public int countModelSubscriptions(String pubSetHash) {
        return modelSubscriptionMapper.countModelSubscriptions(pubSetHash);
    }

    @Override
    public List<Subscribers> querySubscribers(ClueSubscriberId clue) {
        return modelSubscriptionMapper.querySubscriberId(
                clue.getId());
    }


}
