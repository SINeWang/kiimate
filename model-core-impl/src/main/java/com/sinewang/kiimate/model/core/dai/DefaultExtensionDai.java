package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionMapper;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import one.kii.summer.beans.utils.UniqueFinder;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Conflict;
import one.kii.summer.io.exception.NotFound;
import one.kii.summer.io.exception.Panic;
import one.kii.summer.io.validator.NotBadRequest;
import one.kii.summer.io.validator.NotBadResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

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
    public Record loadLast(ChannelName channel) throws Panic, BadRequest, NotFound {
        logger.debug("before-loadLast: ChannelName:{}", channel);
        NotBadRequest.from(channel);
        Record record = extensionMapper.selectLastExtensionByName(
                channel.getOwnerId(),
                channel.getGroup(),
                channel.getName(),
                channel.getTree(),
                channel.getEndTime());
        logger.debug("after-loadLast: Record:{}", record);
        if (record == null) {
            throw new NotFound(UniqueFinder.find(channel));
        }
        return NotBadResponse.of(record);
    }

    @Override
    public Record loadLast(ChannelId channel) throws Panic, BadRequest, NotFound {
        logger.debug("before-loadLast: ChannelId:{}", channel);
        NotBadRequest.from(channel);

        Record record = extensionMapper.selectLastExtensionById(
                channel.getId(),
                channel.getEndTime());
        logger.debug("after-loadLast: Record:{}", record);
        if(record == null){
            throw new NotFound(UniqueFinder.find(channel));
        }
        return NotBadResponse.of(record);
    }

    @Override
    public Record loadLast(ChannelSet channel) throws Panic, BadRequest {
        logger.debug("before-loadLast: ChannelSet:{}", channel);
        NotBadRequest.from(channel);

        Record record = extensionMapper.selectLastExtensionBySet(
                channel.getSet(),
                channel.getEndTime());
        logger.debug("after-loadLast: Record:{}", record);

        return NotBadResponse.of(record);
    }

    @Override
    public List<Record> search(ClueGroup clue) throws BadRequest, Panic {
        logger.debug("before-search: ClueGroup:{}", clue);
        NotBadRequest.from(clue);

        List<Record> list = extensionMapper.queryExtensionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
        logger.debug("after-loadLast: List<Record>:{}", list);

        return NotBadResponse.of(list);
    }


    @Override
    public void remember(Record record) throws Conflict, BadRequest {
        logger.debug("before-remember: Record:{}", record);

        NotBadRequest.from(record);
        MultiValueMap<String, String> conflicts = UniqueFinder.find(record);
        Record lastRecord = extensionMapper.selectExtensionByConflictFactor(conflicts.toSingleValueMap());
        logger.debug("after-lastRecord: Record:{}", record);

        if (lastRecord != null) {
            throw new Conflict(conflicts.keySet());
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

    @Override
    public void forget(Long id) {
        logger.debug("before-forget: Id:{}", id);
        extensionMapper.revoke(
                id,
                new Date()
        );

    }
}
