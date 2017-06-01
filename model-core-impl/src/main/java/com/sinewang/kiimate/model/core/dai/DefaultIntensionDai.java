package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.IntensionMapper;
import one.kii.kiimate.model.core.dai.IntensionDai;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
    public void remember(Record record) throws IntensionDuplicated {
        Date now = new Date();

        Record oldRecord = intensionMapper.selectLatestIntensionByExtIdField(record.getExtId(), record.getField());
        if (oldRecord != null) {
            throw new IntensionDai.IntensionDuplicated(record.getId());
        }
        try {
            intensionMapper.insertIntension(
                    record.getId(),
                    record.getCommit(),
                    record.getExtId(),
                    record.getField(),
                    record.isSingle(),
                    record.getStructure(),
                    record.getRefPubSet(),
                    record.getVisibility(),
                    record.isRequired(),
                    now
            );
        } catch (DuplicateKeyException duplicated) {
            logger.error("Duplicated-Key:{}", record.getId());
            throw new IntensionDai.IntensionDuplicated(record.getId());
        }

    }

    @Override
    public List<Record> loadLatest(ChannelExtension channel) {
        return intensionMapper.selectLatestIntensionsByExtId(channel.getId());
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
