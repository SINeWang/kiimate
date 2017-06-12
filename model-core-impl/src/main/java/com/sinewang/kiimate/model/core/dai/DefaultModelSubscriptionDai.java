package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelSubscriptionMapper;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;
import one.kii.summer.beans.utils.ConflictFinder;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.xyz.*;
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
    public void remember(Instance instance) throws Conflict {
        Map<String, Object> map = ConflictFinder.find(instance);
        int count = modelSubscriptionMapper.countByConflictKeys(map);
        if (count > 0) {
            throw new Conflict(map.keySet().toArray(new String[0]));
        }
        modelSubscriptionMapper.insertSubscription(
                instance.getId(),
                instance.getSubSet(),
                instance.getSubscriberId(),
                instance.getGroup(),
                instance.getName(),
                instance.getTree(),
                instance.getOperatorId(),
                instance.getBeginTime()
        );
    }

    @Override
    public VisitUpInsight getModelPubSetByStatusId(VisitUpWithId channel) throws Panic {
        VisitUpInsight record = modelSubscriptionMapper.selectModelPubSetByStatusId(
                channel.getSubscriberId(),
                channel.getId()
        );
        return NotBadResponse.of(record);
    }

    @Override
    public VisitUpInsight getModelPubSetByXyz(VisitUpWithXyz channel) throws Panic {
        VisitUpInsight record = modelSubscriptionMapper.selectModelPubSetByGroupNameTree(
                channel.getSubscriberId(),
                channel.getGroup(),
                channel.getName(),
                channel.getTree()
        );
        return NotBadResponse.of(record);
    }

    @Override
    public VisitDownInsight selectModelBySet(VisitDownWithSet channel) throws Panic {
        VisitDownInsight record = modelSubscriptionMapper.selectModelBySet(
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
