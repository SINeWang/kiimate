package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.beans.annotations.KeyFactor;
import one.kii.summer.io.exception.NotFound;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface ExtensionDai {

    Extension loadLastExtension(ChannelId channel) throws NotFound;

    List<Extension> queryExtension(ClueGroup clue);

    @Data
    class ClueGroup{
        String ownerId;
        String group;
    }

    @Data
    class ChannelId{
        String id;
        Date beginTime;
    }

    @Transactional
    void insertExtension(Extension extension) throws ExtensionDuplicated;

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

        private String visibility;

        private Date beginTime;

        private Date endTime;

    }

    class ExtensionDuplicated extends Exception {

        @Getter
        private String extId;

        public ExtensionDuplicated(String extId) {
            this.extId = extId;
        }
    }


}
