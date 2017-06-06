package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelSubscriptionMapper;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.ConflictFinder;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultModelSubscriptionDai implements ModelSubscriptionDai {


    @Autowired
    private ModelSubscriptionMapper modelSubscriptionMapper;


    @Override
    public void remember(Status status) throws Conflict {
        Map<String, Object> map = ConflictFinder.find(status);
        int count = modelSubscriptionMapper.countByConflictKeys(map);
        if (count > 0) {
            throw new Conflict(map.keySet().toArray(new String[0]));
        }
        modelSubscriptionMapper.insertSubscription(
                status.getId(),
                status.getSubSet(),
                status.getSubscriberId(),
                status.getGroup(),
                status.getName(),
                status.getTree(),
                status.getOperatorId(),
                status.getBeginTime()
        );
    }

    @Override
    public ModelPubSet getModelPubSetByStatusId(StatusId channel) throws Panic {
        ModelPubSet record = modelSubscriptionMapper.selectModelPubSetByStatusId(
                channel.getId());
        return NotBadResponse.of(record);
    }

    @Override
    public List<Status> querySubscriptions(ClueGroup clue) throws BadRequest {
        NotBadRequest.from(clue);
        return modelSubscriptionMapper.querySubscriptionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
    }

    @Override
    public Status selectSubscription(ChannelGroupNameTree channel) throws Panic, BadRequest {
        NotBadRequest.from(channel);
        Status record = modelSubscriptionMapper.selectSubscriptionByOwnerGroupNameTree(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getTree()
        );
        return NotBadResponse.of(record);

    }

    @Override
    public Status selectSubscription(StatusId channel) throws Panic, BadRequest {
        NotBadRequest.from(channel);
        Status record = modelSubscriptionMapper.selectBySubId(channel.getId());
        return NotBadResponse.of(record);
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
