package com.sinewang.metamate.core.dai;

import com.sinewang.metamate.core.dai.mapper.ExtensionMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import wang.yanjiong.metamate.core.dai.ExtensionDai;
import wang.yanjiong.metamate.core.model.Extension;

/**
 * Created by WangYanJiong on 3/23/17.
 */

@Mapper
public class DefaultExtentionDai implements ExtensionDai {

    @Autowired
    private ExtensionMapper extensionMapper;

    public void setExtensionMapper(ExtensionMapper extensionMapper) {
        this.extensionMapper = extensionMapper;
    }

    public Extension getExtensionById(String id) {
        if (null == id) {
            throw new NullPointerException("id is NULL");
        }
        if (id.trim().length() == 0) {
            throw new NullPointerException("id is EMPTY");
        }
        return extensionMapper.selectExtensionById(id);
    }
}
