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
    public Extension loadLastExtension(ChannelId channel) throws NotFound {
        if (channel == null) {
            throw new NotFound(KeyFactorTools.find(ChannelId.class));
        }
        Extension extension;
        if (channel.getBeginTime() == null) {
            extension = extensionMapper.selectLatestExtensionById(channel.getId());
        } else {
            extension = extensionMapper.selectLastExtensionByIdTime(channel.getId(), channel.getBeginTime());
        }
        if (extension == null) {
            throw new NotFound(KeyFactorTools.find(ChannelId.class));
        }
        return extension;
    }

    @Override
    public List<Extension> queryExtension(ClueGroup clue) {
        return extensionMapper.queryExtensionsByOwnerGroup(
                clue.getOwnerId(),
                clue.getGroup());
    }


    @Override
    public void insertExtension(Extension extension) throws ExtensionDuplicated {
        Date now = new Date();
        Extension lastExtension = extensionMapper.selectLatestExtensionById(extension.getId());

        if (lastExtension != null) {
            throw new ExtensionDuplicated(String.valueOf(extension.getId()));
        }

        try {
            extensionMapper.insertExtension(
                    extension.getId(),
                    extension.getOwnerId(),
                    extension.getGroup(),
                    extension.getName(),
                    extension.getTree(),
                    extension.getVisibility(),
                    now
            );
        } catch (DuplicateKeyException duplicated) {
            logger.error("Duplicated-Key:{}", extension.getId());
        }
    }
}
