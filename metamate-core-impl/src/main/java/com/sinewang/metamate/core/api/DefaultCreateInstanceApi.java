package com.sinewang.metamate.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import wang.yanjiong.metamate.core.api.CreateInstanceApi;
import wang.yanjiong.metamate.core.dai.InstanceDai;
import wang.yanjiong.metamate.core.fi.AnInstanceFormParser;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */
@RestController
public class DefaultCreateInstanceApi implements CreateInstanceApi {

    private static final Logger logger = LoggerFactory.getLogger(DefaultCreateInstanceApi.class);

    @Autowired
    private InstanceDai instanceDai;

    @Autowired
    private AnInstanceFormParser instanceFormParser;


    @Override
    public Receipt createInstanceViaFormUrlEncoded(@PathVariable("group") String group,
                                                   @PathVariable("name") String name,
                                                   @PathVariable("version") String version,
                                                   @RequestHeader("X-MM-Owner-Id") String ownerId,
                                                   @RequestHeader("X-MM-Operator-Id") String operatorId,
                                                   HttpServletRequest request) {

        List<AnInstanceFormParser.Instance> instances = instanceFormParser.parse(group, name, version, ownerId, operatorId, request.getParameterMap());

        List<InstanceDai.Instance> instances1 = new ArrayList<>();

        for (AnInstanceFormParser.Instance instance : instances) {
            InstanceDai.Instance instance1 = new InstanceDai.Instance();
            BeanUtils.copyProperties(instance, instance1);

            if (instance.getValue().length == 1) {
                instance1.setValue(instance.getValue()[0]);
                instances1.add(instance1);
            }

        }

        try {
            instanceDai.insertInstances(instances1);
        } catch (InstanceDai.InstanceDuplicated instanceDuplicated) {
            instanceDuplicated.printStackTrace();
        }


        return null;
    }
}
