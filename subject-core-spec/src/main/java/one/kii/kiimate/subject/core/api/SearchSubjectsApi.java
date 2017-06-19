package one.kii.kiimate.subject.core.api;

import lombok.Data;
import one.kii.summer.asdf.api.SearchApi;
import one.kii.summer.io.context.ReadContext;
import one.kii.summer.io.exception.BadRequest;
import one.kii.summer.io.exception.Panic;

import java.util.List;

/**
 * Created by WangYanJiong on 15/05/2017.
 */
public interface SearchSubjectsApi extends SearchApi<SearchSubjectsApi.Subjects, ReadContext, SearchSubjectsApi.Form> {

    List<Subjects> search(ReadContext context, Form form) throws BadRequest, Panic;


    enum ObjectType {
        UNKNOWN,
        GLIMPSE,
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
}
