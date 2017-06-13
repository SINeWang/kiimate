package com.sinewang.kiimate.model.core.dai;

import com.sinewang.kiimate.model.core.dai.mapper.ExtensionProvidersMapper;
import one.kii.kiimate.model.core.dai.ExtensionProvidersDai;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 08/05/2017.
 */

@Component
public class DefaultExtensionProvidersDai implements ExtensionProvidersDai {


    @Autowired
    private ExtensionProvidersMapper extensionProvidersMapper;

    @Override
    public List<Providers> queryProviders(ClueId clue) {
        return extensionProvidersMapper.queryOwners(
                clue.getId()
        );
    }
}
