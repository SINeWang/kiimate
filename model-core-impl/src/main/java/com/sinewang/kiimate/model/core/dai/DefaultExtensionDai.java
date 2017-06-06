package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionMapper;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.summer.beans.utils.ConflictFinder;
import one.kii.summer.io.annotations.MayHave;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@Component
public class DefaultExtensionDai implements ExtensionDai {

    private final Logger logger = LoggerFactory.getLogger(DefaultExtensionDai.class);

    @Autowired
    private ExtensionMapper extensionMapper;

    @Override
    public Record loadLast(ChannelCoordinate channel) throws Panic, BadRequest {
        NotBadRequest.from(channel);

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
        return NotBadResponse.of(Record.class, MayHave.class, record);
    }

    @Override
    public Record loadLast(ChannelId channel) throws Panic, BadRequest {
        NotBadRequest.from(channel);

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
        return NotBadResponse.of(Record.class, MayHave.class, record);
    }

    @Override
    public List<Record> search(ClueGroup clue) throws BadRequest {
        NotBadRequest.from(clue);

        return extensionMapper.queryExtensionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
    }


    @Override
    public void remember(Record record) throws Conflict, BadRequest {
        NotBadRequest.from(record);
        Map<String, Object> conflicts = ConflictFinder.find(record);
        Record lastRecord = extensionMapper.selectExtensionByConflictFactor(conflicts);
        if (lastRecord != null) {
            throw new Conflict(conflicts.keySet().toArray(new String[0]));
        }
        extensionMapper.insertExtension(
                record.getId(),
                record.getCommit(),
                record.getOwnerId(),
                record.getGroup(),
                record.getName(),
                record.getTree(),
                record.getVisibility(),
                record.getOperatorId(),
                record.getBeginTime()
        );
    }
}
