package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.summer.codec.annotations.HashFactor;
import one.kii.summer.io.context.WriteContext;
import one.kii.summer.io.exception.BadRequest;


/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnExtensionExtractor {


    Extension extract(WriteContext context, DeclareExtensionApi.CommitForm commitForm) throws BadRequest;

    void hashId(Extension extension);

    enum Visibility {
        PRIVATE,
        PROTECTED,
        PUBLIC
    }

    @Data
    class Extension {

        private String id;

        @HashFactor
        private String ownerId;

        @HashFactor
        private String group;

        @HashFactor
        private String name;

        @HashFactor
        private String tree;

        @HashFactor
        private String visibility;
    }


}
