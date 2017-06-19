package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelSubscriptionMapper;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.UniqueFinder;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Created by WangYanJiong on 4/6/17.
 */

@Component
public class DefaultModelSubscriptionDai implements ModelSubscriptionDai {


    @Autowired
    private ModelSubscriptionMapper modelSubscriptionMapper;


    @Override
    public void remember(Instance instance) throws Conflict {
        MultiValueMap<String, String> map = UniqueFinder.find(instance);
        int count = modelSubscriptionMapper.countByConflictKeys(map.toSingleValueMap());
        if (count > 0) {
            throw new Conflict(map.keySet());
        }
        modelSubscriptionMapper.insertSubscription(
                instance.getId(),
                instance.getSet(),
                instance.getSubscriberId(),
                instance.getGroup(),
                instance.getName(),
                instance.getTree(),
                instance.getOperatorId(),
                instance.getBeginTime()
        );
    }

    @Override
    public Instance load(ClueModelSubId id) throws Panic{
        Instance instance = modelSubscriptionMapper.loadInstance(
                id.getId()
        );
        return NotBadResponse.of(instance);
    }

    @Override
    public InsideView loadModelSubById(ZoomInById channel) throws Panic {
        InsideView record = modelSubscriptionMapper.selectModelSubById(
                channel.getSubscriberId(),
                channel.getId()
        );
        return NotBadResponse.of(record);
    }

    @Override
    public InsideView loadModelSubByName(ZoomInByName channel) throws Panic {
        InsideView record = modelSubscriptionMapper.selectModelSubByName(
                channel.getSubscriberId(),
                channel.getGroup(),
                channel.getName(),
                channel.getTree()
        );
        return NotBadResponse.of(record);
    }

    @Override
    public OutsideView selectModelBySet(ZoomOutBySet channel) throws Panic {
        OutsideView record = modelSubscriptionMapper.selectModelPubBySet(
                channel
        );
        return NotBadResponse.of(record);
    }

    @Override
    public List<Instance> querySubscriptions(ClueGroup clue) throws BadRequest, Panic {
        NotBadRequest.from(clue);
        List<Instance> list = modelSubscriptionMapper.querySubscriptionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
        return NotBadResponse.of(list);
    }

    @Override
    public Integer countModelSubscriptions(Long pubSet) {
        return modelSubscriptionMapper.countModelSubscriptions(pubSet);
    }

    @Override
    public List<Subscribers> querySubscribers(ClueSubscriberId clue) throws Panic {
        List<Subscribers> list = modelSubscriptionMapper.querySubscriberId(
                clue.getId());
        return NotBadResponse.of(list);
    }


}
