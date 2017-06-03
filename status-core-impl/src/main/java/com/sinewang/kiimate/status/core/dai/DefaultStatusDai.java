package com.sinewang.kiimate.status.core.dai;

import com.sinewang.kiimate.status.core.dai.mapper.StatusMapper;
import one.kii.kiimate.status.core.dai.AssetDai;
import one.kii.kiimate.status.core.dai.StatusDai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Component
public class DefaultStatusDai implements StatusDai {


    @Autowired
    private StatusMapper statusMapper;

    @Override
    public Date save(Record record) {
        Date now = new Date();
        AssetDai.Asset previous = record.getPrevious();
        if (previous != null) {
            statusMapper.revokeStatus(
                    previous.getProviderId(),
                    previous.getPubSet(),
                    now
            );
        }
        for (Entry entry : record.getEntries()) {
            statusMapper.insertStatus(
                    entry.getId(),
                    record.getPubSet(),
                    entry.getProviderId(),
                    entry.getSubId(),
                    entry.getInsId(),
                    entry.getVersion(),
                    entry.getVisibility(),
                    entry.getStability(),
                    record.getOperatorId(),
                    now
            );
        }
        return now;
    }

    @Override
    public List<Status> query(ClueGroup clue) {
        return statusMapper.queryStatuses(
                clue.getOwnerId(),
                clue.getGroup());
    }
}
