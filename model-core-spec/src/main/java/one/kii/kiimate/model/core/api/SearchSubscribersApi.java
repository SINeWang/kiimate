package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;

import java.util.List;

/**
 * Created by WangYanJiong on 11/05/2017.
 */
public interface SearchSubscribersApi {


    List<Subscribers> search(ReadContext context, QueryForm form);


    @Data
    class Subscribers {
        String subscriberId;
    }

    @Data
    class QueryForm {
        private String subscriberId;
    }

}
