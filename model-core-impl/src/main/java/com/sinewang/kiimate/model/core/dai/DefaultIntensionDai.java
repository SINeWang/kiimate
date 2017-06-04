package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.IntensionMapper;
import one.kii.kiimate.model.core.dai.IntensionDai;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/27/17.
 */

@Component
public class DefaultIntensionDai implements IntensionDai {

    private final Logger logger = LoggerFactory.getLogger(DefaultIntensionDai.class);

    @Autowired
    private IntensionMapper intensionMapper;

    @Override
    public void remember(Record record) throws Conflict {
        Record oldRecord = intensionMapper.selectIntensionByCommit(record.getCommit());
        if (oldRecord != null) {
            throw new Conflict(KeyFactorTools.find(Record.class));
        }
        intensionMapper.insertIntension(
                record.getId(),
                record.getCommit(),
                record.getExtId(),
                record.getField(),
                record.getSingle(),
                record.getStructure(),
                record.getRefPubSet(),
                record.getVisibility(),
                record.getRequired(),
                record.getOperatorId(),
                record.getBeginTime()
        );

    }

    @Override
    public List<Record> load(ChannelLatestExtension channel) throws BadRequest, Panic {
        List<Record> records = intensionMapper.selectLatestIntensionsByExtId(channel.getId());
        if (records.isEmpty()) {
            throw new BadRequest("id");
        }
        return records;
    }


    @Override
    public List<Record> loadLast(ChannelLastExtension channel) {
        if (channel.getBeginTime() == null) {
            return intensionMapper.selectLatestIntensionsByExtId(channel.getId());
        } else {
            return intensionMapper.selectLastIntensionsByExtId(
                    channel.getId(),
                    channel.getBeginTime());
        }
    }

    @Override
    public List<Record> loadLast(ChannelPubSet pubSet) {
        List<String> fields = intensionMapper.selectLastFieldsByExtIdPubSet(
                pubSet.getExtId(),
                pubSet.getPubSet(),
                pubSet.getBeginTime());
        List<Record> records = new ArrayList<>();
        for (String field : fields) {
            Record record = intensionMapper.selectLastIntensionByExtIdField(
                    pubSet.getExtId(),
                    field);
            records.add(record);
        }
        return records;
    }


    @Override
    public void forget(ChannelId channel) {
        Date now = new Date();
        intensionMapper.updateLatestIntensionEndTimeById(
                channel.getId(),
                now);
    }
}
