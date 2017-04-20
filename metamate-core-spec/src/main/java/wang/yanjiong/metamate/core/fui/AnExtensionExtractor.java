package wang.yanjiong.metamate.core.fui;

import lombok.Data;
import one.kii.summer.io.exception.BadRequest;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;

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
