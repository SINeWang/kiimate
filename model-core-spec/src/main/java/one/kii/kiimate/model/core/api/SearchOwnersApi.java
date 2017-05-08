package one.kii.kiimate.model.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;

import java.util.List;

/**
 * Created by WangYanJiong on 02/05/2017.
 */
public interface SearchOwnersApi {


    List<Owners> search(ReadContext context, QueryForm form);


    @Data
    class Owners {
        String ownerId;
    }

    @Data
    class QueryForm {
        private String ownerId;
    }

}
