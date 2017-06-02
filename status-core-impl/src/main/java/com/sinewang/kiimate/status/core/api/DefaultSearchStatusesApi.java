package com.sinewang.kiimate.status.core.api;

import one.kii.kiimate.status.core.api.SearchStatusesApi;
import one.kii.kiimate.status.core.dai.StatusesDai;
import one.kii.summer.beans.utils.ValueMapping;
import one.kii.summer.io.context.ReadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
@Component
public class DefaultSearchStatusesApi implements SearchStatusesApi {

    @Autowired
    private StatusesDai statusesDai;

    @Override
    public List<Statuses> search(ReadContext context, QueryForm form) {
        StatusesDai.ClueGroup clue = ValueMapping.from(StatusesDai.ClueGroup.class, context);
        clue.setGroup(form.getQuery());
        List<StatusesDai.Status> statusList = statusesDai.query(clue);
        return ValueMapping.from(Statuses.class, statusList);
    }


}
