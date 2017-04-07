package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.ExtensionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@Service
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
    public void deleteExtensionById(String id) {
        extensionMapper.deleteExtensionById(id);
    }

    @Override
    public void insertExtension(Extension extension) throws ExtensionDuplicated {
        Date now = new Date();
        Extension lastExtension = extensionMapper.selectExtensionById(extension.getId());

        if (lastExtension != null) {
            extensionMapper.updateEndTimeExtensionById(extension.getId(), now);
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
            throw new ExtensionDuplicated(extension.getId(), duplicated);
        }
    }
}
