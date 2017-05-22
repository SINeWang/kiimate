package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionMapper;
import one.kii.kiimate.model.core.dai.ExtensionDai;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

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
    public Extension selectExtensionById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new NullPointerException("id is EMPTY");
        }
        return extensionMapper.selectExtensionById(id);
    }

    @Override
    public List<Extension> selectExtensionsByOwnerGroup(String ownerId, String group) {
        return extensionMapper.selectExtensionsByOwnerGroup(ownerId, group);
    }

    @Override
    public List<Extension> queryExtensionsByOwnerGroup(String ownerId, String group) {
        return extensionMapper.queryExtensionsByOwnerGroup(ownerId, group);
    }


    @Override
    public void insertExtension(Extension extension) throws ExtensionDuplicated {
        Date now = new Date();
        Extension lastExtension = extensionMapper.selectExtensionById(extension.getId());

        if (lastExtension != null) {
            throw new ExtensionDuplicated(extension.getId());
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
