package com.sinewang.metamate.core.api;

import one.kii.summer.beans.utils.DataTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wang.yanjiong.metamate.core.api.ExploreModelsApi;
import wang.yanjiong.metamate.core.dai.ModelPublicationDai;

import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */
@Component
public class DefaultExploreModelsApi implements ExploreModelsApi {


    @Autowired
    private ModelPublicationDai modelPublicationDai;

    @Override
    public List<Provider> getProviders(String query) {
        List<ModelPublicationDai.Provider> providerList = modelPublicationDai.getProviders(query);

        return DataTools.copy(providerList, Provider.class);
    }
}
