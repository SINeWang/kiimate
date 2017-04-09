package wang.yanjiong.metamate.core.fi;

import lombok.Data;
import wang.yanjiong.metamate.core.api.DeclareExtensionApi;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface AnExtensionExtractor {

    Extension extract(DeclareExtensionApi.Form form, String ownerId) throws MissingParamException;

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

    class MissingParamException extends Exception {
        public MissingParamException(String message) {
            super(message);
        }
    }

}
