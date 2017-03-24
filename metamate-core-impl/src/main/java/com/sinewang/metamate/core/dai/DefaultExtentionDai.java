package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.ExtensionMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@Component
public class DefaultExtentionDai implements ExtensionDai {

    @Autowired
    private ExtensionMapper extensionMapper;

    public Extension getExtensionById(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new NullPointerException("id is EMPTY");
        }
        return extensionMapper.selectExtensionById(id);
    }
}
