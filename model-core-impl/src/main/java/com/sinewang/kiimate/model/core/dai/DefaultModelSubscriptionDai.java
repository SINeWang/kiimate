package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ModelSubscriptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import one.kii.kiimate.model.core.dai.ModelSubscriptionDai;

import java.util.Date;

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
                modelSubscription.getSubSetHash(),
                modelSubscription.getSubscriberId(),
                modelSubscription.getGroup(),
                modelSubscription.getName()
        );
        if (count > 0) {
            throw new DuplicatedSubscription(
                    modelSubscription.getSubSetHash(),
                    modelSubscription.getSubscriberId(),
                    modelSubscription.getGroup(),
                    modelSubscription.getName()
            );
        }
        Date now = new Date();
        modelSubscriptionMapper.insertSubscription(
                modelSubscription.getId(),
                modelSubscription.getSubSetHash(),
                modelSubscription.getSubscriberId(),
                modelSubscription.getGroup(),
                modelSubscription.getName(),
                modelSubscription.getTree(),
                modelSubscription.getOperatorId(),
                now
        );

    }

    @Override
    public String getLatestRootExtIdBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree) {
        return modelSubscriptionMapper.selectLatestSubscriptionBySubscriberIdGroupNameTree(
                subscriberId, group, name, tree
        );
    }

    @Override
    public String getLatestSubIdBySubscriberIdGroupNameTree(String subscriberId, String group, String name, String tree) {
        return modelSubscriptionMapper.selectLatestSubIdSubscriberIdGroupNameTree(
                subscriberId, group, name, tree
        );
    }

    @Override
    public int countModelSubscriptions(String pubSetHash) {
        return modelSubscriptionMapper.countModelSubscriptions(pubSetHash);
    }

}
