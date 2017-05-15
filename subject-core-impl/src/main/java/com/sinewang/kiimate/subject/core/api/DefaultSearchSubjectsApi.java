package com.sinewang.kiimate.subject.core.api;

import one.kii.kiimate.model.core.dai.OwnersDai;
import one.kii.kiimate.subject.core.api.SearchSubjectsApi;
import one.kii.summer.beans.utils.DataTools;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 15/05/2017.
 */

@Component
public class DefaultSearchSubjectsApi implements SearchSubjectsApi {

    @Autowired
    private OwnersDai ownersDai;

    @Override
    public List<Subjects> search(ReadContext context, Form form) {
        List<OwnersDai.Owners> owners = ownersDai.queryOwners(form.getGroup());
        List<Subjects> subjects = DataTools.copy(owners, Subjects.class);
        return subjects;
    }
}
