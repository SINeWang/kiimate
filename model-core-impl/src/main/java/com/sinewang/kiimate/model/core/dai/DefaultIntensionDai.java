package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.IntensionMapper;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.beans.utils.UniqueFinder;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultIntensionDai implements IntensionDai {


    @Autowired
    private IntensionMapper intensionMapper;

    @Override
    public void remember(Record record) throws Conflict, BadRequest {
        NotBadRequest.from(record);
        MultiValueMap<String, String> conflicts = UniqueFinder.find(record);
        Record oldRecord = intensionMapper.selectIntensionByConflictKey(conflicts.toSingleValueMap());
        if (oldRecord != null) {
            throw new Conflict(conflicts.keySet());
        }
        intensionMapper.insertIntension(
                record.getId(),
                record.getCommit(),
                record.getExtId(),
                record.getField(),
                record.getSingle(),
                record.getStructure(),
                record.getRefSet(),
                record.getVisibility(),
                record.getRequired(),
                record.getOperatorId(),
                record.getBeginTime()
        );

    }

    @Override
    public List<Record> loadLast(ChannelExtensionId channel) throws BadRequest, Panic {
        NotBadRequest.from(channel);
        List<Record> records;
        if (channel.getBeginTime() == null) {
            records = intensionMapper.selectLatestIntensionsByExtId(channel.getId());
        } else {
            records = intensionMapper.selectLastIntensionsByExtId(
                    channel.getId(),
                    channel.getBeginTime());
        }
        return NotBadResponse.of(records);
    }

    @Override
    public List<Record> loadLast(ChannelPubSet channel) throws BadRequest, Panic {
        NotBadRequest.from(channel);
        List<String> fields = intensionMapper.selectLastFieldsByExtIdPubSet(
                channel.getExtId(),
                channel.getPubSet(),
                channel.getBeginTime());
        List<Record> records = new ArrayList<>();
        for (String field : fields) {
            Record record = intensionMapper.selectLastIntensionByExtIdField(
                    channel.getExtId(),
                    field);
            records.add(record);
        }
        return NotBadResponse.of(records);
    }


    @Override
    public void forget(ChannelId channel) throws BadRequest {
        NotBadRequest.from(channel);
        Date now = new Date();
        intensionMapper.updateLatestIntensionEndTimeById(
                channel.getId(),
                now);
    }
}
