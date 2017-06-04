package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.StatusMapper;
import one.kii.kiimate.status.core.dai.StatusDai;
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
    public List<Statuses> query(ClueGroup clue) {
        return statusMapper.queryStatuses(
                clue.getOwnerId(),
                clue.getGroup());
    }
}
