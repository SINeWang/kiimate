package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionMapper;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.summer.beans.utils.KeyFactorTools;
import one.kii.summer.io.exception.NotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@Component
public class DefaultExtensionDai implements ExtensionDai {

    private final Logger logger = LoggerFactory.getLogger(DefaultExtensionDai.class);

    @Autowired
    private ExtensionMapper extensionMapper;

    @Override
    public Record loadLast(ChannelCoordinate channel) throws NotFound {
        Record record;
        if (channel.getBeginTime() == null) {
            record = extensionMapper.selectLatestExtensionByOwnerGroupNameTree(
                    channel.getOwnerId(),
                    channel.getGroup(),
                    channel.getName(),
                    channel.getTree()
            );
        } else {
            record = extensionMapper.selectLastExtensionByOwnerGroupNameTree(
                    channel.getOwnerId(),
                    channel.getGroup(),
                    channel.getName(),
                    channel.getTree(),
                    channel.getBeginTime());
        }
        if (record == null) {
            throw new NotFound(KeyFactorTools.find(ChannelCoordinate.class));
        }
        return record;
    }

    @Override
    public Record loadLast(ChannelId channel) throws NotFound {
        Record record;
        if (channel.getBeginTime() == null) {
            record = extensionMapper.selectLatestExtensionById(
                    channel.getId()
            );
        } else {
            record = extensionMapper.selectLastExtensionById(
                    channel.getId(),
                    channel.getBeginTime());
        }
        if (record == null) {
            throw new NotFound(KeyFactorTools.find(ChannelCoordinate.class));
        }
        return record;
    }

    @Override
    public List<Record> search(ClueGroup clue) {
        return extensionMapper.queryExtensionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
    }


    @Override
    public void remember(Record record) throws ExtensionDuplicated {
        Date now = new Date();
        Record lastRecord = extensionMapper.selectLatestExtensionById(record.getId());

        if (lastRecord != null) {
            throw new ExtensionDuplicated(String.valueOf(record.getId()));
        }

        try {
            extensionMapper.insertExtension(
                    record.getId(),
                    record.getCommit(),
                    record.getOwnerId(),
                    record.getGroup(),
                    record.getName(),
                    record.getTree(),
                    record.getVisibility(),
                    now
            );
        } catch (DuplicateKeyException duplicated) {
            logger.error("Duplicated-Key:{}", record.getId());
        }
    }
}
