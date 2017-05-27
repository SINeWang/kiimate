package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionMapper;
import com.sinewang.kiimate.model.core.dai.mapper.IntensionMapper;
import one.kii.kiimate.model.core.dai.ExtensionDai;
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

    @Autowired
    private ExtensionMapper extensionMapper;

    @Override
    public void insertIntension(Intension intension) throws IntensionDuplicated {
        Date now = new Date();

        Intension oldIntension = intensionMapper.selectLatestIntensionByExtIdField(intension.getExtId(), intension.getField());
        if (oldIntension != null) {
            throw new IntensionDai.IntensionDuplicated(intension.getId());
        }
        try {
            intensionMapper.insertIntension(
                    intension.getId(),
                    intension.getExtId(),
                    intension.getField(),
                    intension.isSingle(),
                    intension.getStructure(),
                    intension.getRefExtId(),
                    intension.getVisibility(),
                    intension.isRequired(),
                    now
            );
        } catch (DuplicateKeyException duplicated) {
            logger.error("Duplicated-Key:{}", intension.getId());
            throw new IntensionDai.IntensionDuplicated(intension.getId());
        }

        ExtensionDai.Extension extension = extensionMapper.selectLatestExtensionById(intension.getExtId());

        extensionMapper.updateEndTimeExtensionById(intension.getExtId(), now);

        extension.setBeginTime(now);

        extensionMapper.insertExtension(
                extension.getId(),
                extension.getOwnerId(),
                extension.getGroup(),
                extension.getName(),
                extension.getTree(),
                extension.getVisibility(),
                now
        );
    }

    @Override
    public List<Intension> loadLatestIntensions(ChannelExtension channel) {
        return intensionMapper.selectLatestIntensionsByExtId(channel.getId());
    }

    @Override
    public List<Intension> loadLastIntensions(ChannelExtension extension) {
        List<String> fields = intensionMapper.selectLastFieldsByExtId(
                extension.getId(),
                extension.getBeginTime(),
                extension.getEndTime());
        List<Intension> intensions = new ArrayList<>();
        for (String field : fields) {
            Intension intension = intensionMapper.selectLastIntensionByExtIdField(extension.getId(), field);
            intensions.add(intension);
        }
        return intensions;
    }

    @Override
    public List<Intension> loadLastIntensions(ChannelPubSet pubSet) {
        List<String> fields = intensionMapper.selectLastFieldsByExtIdPubSet(
                pubSet.getId(),
                pubSet.getPubSet(),
                pubSet.getBeginTime(),
                pubSet.getEndTime());
        List<Intension> intensions = new ArrayList<>();
        for (String field : fields) {
            Intension intension = intensionMapper.selectLastIntensionByExtIdField(pubSet.getId(), field);
            intensions.add(intension);
        }
        return intensions;
    }


    @Override
    public void removeIntension(String intId) {
        Date now = new Date();
        intensionMapper.updateLatestIntensionEndTimeById(intId, now);
    }
}
