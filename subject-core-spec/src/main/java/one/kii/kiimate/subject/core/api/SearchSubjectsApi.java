package one.kii.kiimate.subject.core.api;

import lombok.Data;
import one.kii.summer.io.context.ReadContext;

import java.util.List;

/**
 * Created by WangYanJiong on 15/05/2017.
 */
public interface SearchSubjectsApi {

    List<Subjects> search(ReadContext context, Form form);


    @Data
    class Subjects {
        String id;
        String group;
        String name;
    }

    @Data
    class Form {
        ObjectType objectType;
        AccessType accessType;
        String group;
    }

    enum ObjectType {
        UNKNOWN,
        ASSET,
        EXTENSION,
        EVENT,
        INTENSION,
        INSTANCE,
        MODEL,
        STATUS
    }

    enum AccessType {
        UNKNOWN,
        OWNER,
        PROVIDER,
        SUBSCRIBER,
        VISITOR,
        OPERATOR
    }
}
