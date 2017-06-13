package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.StatusMapper;
import one.kii.kiimate.status.core.dai.StatusDai;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import one.kii.summer.zoom.OutsideView;
import one.kii.summer.zoom.ZoomOutByName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Component
public class DefaultStatusDai implements StatusDai {


    @Autowired
    private StatusMapper statusMapper;


    @Override
    public List<OutsideView> searchDownstream(ClueGroup clue) throws BadRequest, Panic {
        NotBadRequest.from(clue);
        List<OutsideView> list = statusMapper.queryStatuses(
                clue.getOwnerId(),
                clue.getGroup());
        return NotBadResponse.of(list);
    }

    @Override
    public OutsideView loadDownstream(ZoomOutByName channel) throws Panic, BadRequest {
        NotBadRequest.from(channel);
        OutsideView statuses = statusMapper.selectLast(
                channel.getProviderId(),
                channel.getGroup(),
                channel.getName(),
                channel.getStability(),
                channel.getVersion(),
                channel.getEndTime());

        return NotBadResponse.of(statuses);
    }
}
