package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface ExtensionDai {

    Extension selectExtensionById(String id);

    List<Extension> selectExtensionsByOwnerGroup(String ownerId, String group);

    List<Extension> queryExtensionsByOwnerGroup(String ownerId, String group);

    @Transactional
    void insertExtension(Extension extension) throws ExtensionDuplicated;

    @Data
    class Extension {

        private String id;

        private String group;

        private String name;

        private String tree;

        private String ownerId;

        private String visibility;

    }

    class ExtensionDuplicated extends Exception {

        @Getter
        private String extId;

        public ExtensionDuplicated(String extId) {
            this.extId = extId;
        }
    }


}
