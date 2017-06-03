package one.kii.kiimate.status.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 20/05/2017.
 */
public interface SearchStatusesApi {


    List<Statuses> search(ReadContext context, QueryForm form);

    @Data
    class Statuses {
        String id;
        String subSet;
        String ownerId;
        String group;
        String name;
        String tree;
        Date beginTime;
    }

    @Data
    class QueryForm {
        String query;
    }

}
