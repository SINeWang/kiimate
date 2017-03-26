package wang.yanjiong.metamate.core.dai;

import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface ExtensionDai {

    Extension selectExtensionById(String id);

    @Transactional
    void insertExtension(Extension extension) throws ExtensionDuplicated;

    @Data
    class Extension {

        private String id;

        private String group;

        private String name;

        private String version;

        private String visibility;

        private String structure;

    }

    class ExtensionDuplicated extends Exception {
        public ExtensionDuplicated(String message, Throwable e) {
            super(message, e);
        }
    }


}
