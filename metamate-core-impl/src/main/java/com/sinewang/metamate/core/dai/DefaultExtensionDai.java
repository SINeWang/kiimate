package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.ExtensionMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import wang.yanjiong.metamate.core.dai.ExtensionDai;

import java.util.Date;

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
        Extension extension = extensionMapper.selectExtensionById(id);
        return extension;
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
                    extension.getGroup(),
                    extension.getName(),
                    extension.getTree(),
                    extension.getVisibility(),
                    extension.getStructure(),
                    now
            );
        } catch (DuplicateKeyException duplicated) {
            logger.error("Duplicated-Key:{}", extension.getId());
            throw new ExtensionDuplicated(extension.getId(), duplicated);
        }
    }
}
