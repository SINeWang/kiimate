package one.kii.kiimate.model.core.dai;

import lombok.Data;
import lombok.Getter;
import one.kii.summer.beans.annotations.KeyFactor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by WangYanJiong on 3/23/17.
 */
public interface IntensionDai {


    @Transactional
    void insertIntension(Intension intension) throws IntensionDuplicated;

    List<Intension> loadLatestIntensions(ChannelExtension channel);

    List<Intension> loadLastIntensions(ChannelPubSet channel);

    void removeIntension(String intId);

    @Data
    class ChannelExtension {

        String id;

        Date beginTime;

        Date endTime;
    }

    @Data
    class ChannelPubSet {

        String id;

        String pubSet;

        Date beginTime;

        Date endTime;
    }



    @Data
    class Intension {

        private String id;

        @KeyFactor
        private String extId;

        @KeyFactor
        private String field;

        private boolean single;

        private String structure;

        private String refExtId;

        private String visibility;

        private boolean required;
    }

    class IntensionDuplicated extends Exception {

        @Getter
        private String intId;

        public IntensionDuplicated(String intId) {
            this.intId = intId;
        }
    }
}
