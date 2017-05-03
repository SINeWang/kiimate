package one.kii.kiimate.model.core.fui;

import lombok.Data;
import one.kii.kiimate.model.core.api.DeclareExtensionApi;
import one.kii.summer.io.exception.BadRequest;


/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnExtensionExtractor {


    Extension extract(DeclareExtensionApi.CommitForm commitForm) throws BadRequest;

    String hashId(String ownerId, String group, String name, String tree, String visibility);

    @Data
    class Extension {

        private String id;

        private String ownerId;

        private String group;

        private String name;

        private String tree;

        private String visibility;
    }


}
