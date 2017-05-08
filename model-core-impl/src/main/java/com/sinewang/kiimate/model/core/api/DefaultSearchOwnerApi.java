package com.sinewang.kiimate.model.core.api;

import one.kii.kiimate.model.core.api.SearchOwnersApi;
import one.kii.kiimate.model.core.dai.OwnersDai;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 08/05/2017.
 */

@Component
public class DefaultSearchOwnerApi implements SearchOwnersApi {


    @Autowired
    private OwnersDai ownersDai;

    @Override
    public List<Owners> search(ReadContext context, QueryForm form) {
        List<OwnersDai.Owners> ownersList = ownersDai.queryOwners(form.getOwnerId());
        List<Owners> owners = DataTools.copy(ownersList, Owners.class);
        return owners;
    }
}
