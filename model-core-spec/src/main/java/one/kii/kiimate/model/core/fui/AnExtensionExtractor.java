package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.summer.beans.annotations.KeyFactor;
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

        @KeyFactor
        private String ownerId;

        @KeyFactor
        private String group;

        @KeyFactor
        private String name;

        @KeyFactor
        private String tree;

        @KeyFactor
        private String visibility;
    }


}
