package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.InstanceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.dai.InstanceDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@Service
public class DefaultInstanceDai implements InstanceDai {

    @Autowired
    private InstanceMapper instanceMapper;

    @Override
    public void insertInstances(List<Instance> instances) throws InstanceDuplicated {
        Date beginTime = new Date();
        for (Instance instance : instances) {
            Instance latestInstance = instanceMapper.selectLatestInstanceByIntIdOwnerId(
                    instance.getIntId(),
                    instance.getOwnerId());

            if (latestInstance != null) {
                instanceMapper.updateInstanceEndTimeById(latestInstance.getId(), beginTime);
            }
            instanceMapper.insertInstance(
                    instance.getId(),
                    instance.getExtId(),
                    instance.getIntId(),
                    instance.getOwnerId(),
                    instance.getField(),
                    instance.getValue(),
                    instance.getValueRefId(),
                    instance.getOperatorId(),
                    beginTime
            );
        }
    }
}
